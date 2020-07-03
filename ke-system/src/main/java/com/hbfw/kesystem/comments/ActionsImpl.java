package com.hbfw.kesystem.comments;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class ActionsImpl extends Actions {

    private static final Logger LOG = Logger.getLogger(Actions.class.getName());
    private final WebDriver driver;
    private final Map<InputSource, Sequence> sequences = new HashMap();
    private final PointerInput defaultMouse;
    private final KeyInput defaultKeyboard;
    private final Keyboard jsonKeyboard;
    private final Mouse jsonMouse;
    protected CompositeAction action;

    public ActionsImpl(WebDriver driver) {
        super(driver);
        this.defaultMouse = new PointerInput(PointerInput.Kind.MOUSE, "default mouse");
        this.defaultKeyboard = new KeyInput("default keyboard");
        this.action = new CompositeAction();
        this.driver = (WebDriver) Objects.requireNonNull(driver);
        if (driver instanceof HasInputDevices) {
            HasInputDevices deviceOwner = (HasInputDevices)driver;
            this.jsonKeyboard = deviceOwner.getKeyboard();
            this.jsonMouse = deviceOwner.getMouse();
        } else {
            this.jsonKeyboard = null;
            this.jsonMouse = null;
        }
    }

    @Override
    public Actions keyDown(CharSequence key) {
        return super.keyDown(key);
    }

    @Override
    public Actions keyDown(WebElement target, CharSequence key) {
        return super.keyDown(target, key);
    }

    @Override
    public Actions keyUp(CharSequence key) {
        return super.keyUp(key);
    }

    @Override
    public Actions keyUp(WebElement target, CharSequence key) {
        return super.keyUp(target, key);
    }

    @Override
    public Actions sendKeys(CharSequence... keys) {
        return super.sendKeys(keys);
    }

    @Override
    public Actions sendKeys(WebElement target, CharSequence... keys) {
        return super.sendKeys(target, keys);
    }

    @Override
    public Actions clickAndHold(WebElement target) {
        return super.clickAndHold(target);
    }

    @Override
    public Actions clickAndHold() {
        return super.clickAndHold();
    }

    @Override
    public Actions release(WebElement target) {
        return super.release(target);
    }

    @Override
    public Actions release() {
        return super.release();
    }

    @Override
    public Actions click(WebElement target) {
        return super.click(target);
    }

    @Override
    public Actions click() {
        return super.click();
    }

    @Override
    public Actions doubleClick(WebElement target) {
        return super.doubleClick(target);
    }

    @Override
    public Actions doubleClick() {
        return super.doubleClick();
    }

    @Override
    public Actions moveToElement(WebElement target) {
        return super.moveToElement(target);
    }

    @Override
    public Actions moveToElement(WebElement target, int xOffset, int yOffset) {
        return super.moveToElement(target, xOffset, yOffset);
    }

    @Override
    public Actions moveByOffset(int xOffset, int yOffset) {
        if (this.isBuildingActions()) {
            this.action.addAction(new MoveToOffsetAction(this.jsonMouse, (Locatable)null, xOffset, yOffset));
        }

        return this.tick(this.defaultMouse.createPointerMove(Duration.ofMillis(0L), PointerInput.Origin.pointer(), xOffset, yOffset));
    }
    private boolean isBuildingActions() {
        return this.jsonMouse != null || this.jsonKeyboard != null;
    }
    @Override
    public Actions contextClick(WebElement target) {
        return super.contextClick(target);
    }

    @Override
    public Actions contextClick() {
        return super.contextClick();
    }

    @Override
    public Actions dragAndDrop(WebElement source, WebElement target) {
        return super.dragAndDrop(source, target);
    }

    @Override
    public Actions dragAndDropBy(WebElement source, int xOffset, int yOffset) {
        return super.dragAndDropBy(source, xOffset, yOffset);
    }

    @Override
    public Actions pause(long pause) {
        return super.pause(pause);
    }

    @Override
    public Actions pause(Duration duration) {
        return super.pause(duration);
    }

    @Override
    public Actions tick(Interaction... actions) {
        return super.tick(actions);
    }

    @Override
    public Actions tick(Action action) {
        return super.tick(action);
    }

    @Override
    public Action build() {
        return super.build();
    }

    @Override
    public void perform() {
        super.perform();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
