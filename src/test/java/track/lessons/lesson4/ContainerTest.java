package track.lessons.lesson4;

import org.junit.Assert;
import org.junit.Test;
import track.container.Container;
import track.container.JsonConfigReader;
import track.container.beans.Car;
import track.container.beans.Engine;
import track.container.beans.Gear;
import track.container.config.Bean;

import java.io.File;
import java.util.List;

public class ContainerTest {

    @Test
    public void checkCar() throws Exception {
        List<Bean> beans = new JsonConfigReader().parseBeans(new File("src/main/resources/config.json"));
        Container container = new Container(beans);
        Car car = (Car) container.getByClass("track.container.beans.Car");
        Assert.assertTrue(car.getEngine().getPower() == 200);
        Assert.assertTrue(car.getGear().getCount() == 6);
        car = (Car) container.getById("carBean");
        Assert.assertTrue(car.getEngine().getPower() == 200);
        Assert.assertTrue(car.getGear().getCount() == 6);
    }

    @Test
    public void checkGear() throws Exception {
        List<Bean> beans = new JsonConfigReader().parseBeans(new File("src/main/resources/config.json"));
        Container container = new Container(beans);
        Gear gear = (Gear) container.getByClass("track.container.beans.Gear");
        Assert.assertTrue(gear.getCount() == 6);
        gear = (Gear) container.getById("gearBean");
        Assert.assertTrue(gear.getCount() == 6);
    }

    @Test
    public void checkEngine() throws Exception {
        List<Bean> beans = new JsonConfigReader().parseBeans(new File("src/main/resources/config.json"));
        Container container = new Container(beans);
        Engine engine = (Engine) container.getByClass("track.container.beans.Engine");
        Assert.assertTrue(engine.getPower() == 200);
        engine = (Engine) container.getById("engineBean");
        Assert.assertTrue(engine.getPower() == 200);
    }
}
