package track.container;

import java.lang.reflect.Field;
import java.util.*;

import track.container.beans.Engine;
import track.container.config.Bean;
import track.container.config.InvalidConfigurationException;
import track.container.config.Property;
import track.container.config.ValueType;

import static java.lang.String.valueOf;

/**
 * Основной класс контейнера
 * У него определено 2 публичных метода, можете дописывать свои методы и конструкторы
 */
public class Container {

    private Map<String, Object> mapId;
    private List<Bean> beans;

    // Реализуйте этот конструктор, используется в тестах!
    public Container(List<Bean> beans) throws Exception {
        mapId = new HashMap<String, Object>();
        this.beans = beans;
    }

    public static void main(String[] args) throws Exception {
        Engine engine = new Engine();
        Field field = engine.getClass().getField("power");
        String string = field.getType().toString();
        System.out.println(string);
    }

    /**
     *  Вернуть объект по имени бина из конфига
     *  Например, Car car = (Car) container.getById("carBean")
     */
    public Object getById(String id) throws InvalidConfigurationException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        if (mapId.containsKey(id)) {
            return mapId.get(id);
        }
        for (Bean bean : beans) {
            if (bean.getId().equals(id)) {
                Class clazz = Class.forName(bean.getClassName());
                Object obj = clazz.newInstance();
                mapId.put(bean.getId(), obj);
                for ( Property property : bean.getProperties().values()) {
                    Field field = clazz.getDeclaredField(property.getName());
                    field.setAccessible(true);
                    if (property.getType() == ValueType.VAL) {
                        switch (field.getType().toString()) {
                            case "int":
                                field.setInt(obj, Integer.parseInt(property.getValue()));
                                break;
                            case "byte":
                                field.setByte(obj, Byte.parseByte(property.getValue()));
                                break;
                            case "short":
                                field.setShort(obj, Short.parseShort(property.getValue()));
                                break;
                            case "long":
                                field.setLong(obj, Long.parseLong(property.getValue()));
                                break;
                            case "float":
                                field.setFloat(obj, Float.parseFloat(property.getValue()));
                                break;
                            case "double":
                                field.setDouble(obj, Double.parseDouble(property.getValue()));
                                break;
                            case "char":
                                field.setChar(obj, property.getValue().charAt(0));
                                break;
                            case "string":
                                field.set(obj, property.getValue());
                                break;
                            default:
                                throw new InvalidConfigurationException("Неверное имя примитивного типа");
                        }
                    } else {
                        field.set(obj, getById(property.getValue()));
                    }
                }
                return obj;
            }
        }
        return null;
    }

    /**
     * Вернуть объект по имени класса
     * Например, Car car = (Car) container.getByClass("track.container.beans.Car")
     */
    public Object getByClass(String className) throws ClassNotFoundException, NoSuchFieldException, InstantiationException, InvalidConfigurationException, IllegalAccessException {
        for (Bean bean : beans) {
            if (bean.getClassName().equals(className)) {
                return getById(bean.getId());
            }
        }
        return null;
    }
}
