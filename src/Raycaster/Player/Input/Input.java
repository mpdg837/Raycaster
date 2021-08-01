package Raycaster.Player.Input;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Input implements KeyListener, MouseListener, MouseMotionListener {

    public static Point centerMousePos = new Point(160,100);
    private Point mousePos = new Point();

    private ArrayList<Integer> keys = new ArrayList<Integer>();

    private ArrayList<Integer> keysDown = new ArrayList<Integer>() ;
    private ArrayList<Integer> lastKeysDown = new ArrayList<Integer>();

    private ArrayList<Integer>  mouseButton= new ArrayList<Integer>() ;
    private ArrayList<Integer> mouseButtonDown = new ArrayList<Integer>() ;
    private ArrayList<Integer> lastMouseButtonDown = new ArrayList<Integer>();

    private boolean isDragged;
    private boolean isMoving;


    public Point getMousePos(){

        PointerInfo info = MouseInfo.getPointerInfo();
        mousePos = info.getLocation();

        return new Point(mousePos.x-centerMousePos.x,mousePos.y-centerMousePos.y);
    }


    public void resetKey(){
        lastKeysDown.addAll(keysDown);
        lastMouseButtonDown.addAll(mouseButton);


        keysDown.clear();
        mouseButtonDown.clear();
    }

    public void centerMouse(){
        try {
            Robot robot = new Robot();

            robot.mouseMove(centerMousePos.x, centerMousePos.y);
        }catch (AWTException ignore){}

    }

    public boolean isDragged(){return isDragged;}
    public boolean isMoving() {return isMoving;}

    public boolean getMouseButton(int mouseButton){return this.mouseButton.contains(mouseButton);}
    public boolean getMouseButtonDown(int mouseButton){return this.mouseButtonDown.contains(mouseButton);}

    public boolean getKey(int key){
        return keys.contains(key);
    }
    public boolean getKeyDown(int key){
        return keysDown.contains(key);
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!keys.contains(e.getKeyCode())) {
            keys.add( e.getKeyCode());

        }
        if(!lastKeysDown.contains(e.getKeyCode())) {
            keysDown.add(e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(keys.contains(e.getKeyCode())) {
            keys.remove((Integer) e.getKeyCode());
        }

        if(lastKeysDown.contains(e.getKeyCode())) {
            lastKeysDown.remove((Integer) e.getKeyCode());
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseButton.add(e.getButton());
        if(!lastMouseButtonDown.contains(mouseButton)) {
            mouseButtonDown.add(e.getButton());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        endMouse(e.getButton());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        endMouse(e.getButton());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        endMouse(e.getButton());
    }

    private void endMouse(int button){
        if(mouseButton.contains(button)) mouseButton.remove((Integer) button);
        if(lastMouseButtonDown.contains(button)) lastMouseButtonDown.remove((Integer) button);
        isDragged = false;
        isMoving = false;
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        isDragged = true;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        isMoving = true;
    }
}
