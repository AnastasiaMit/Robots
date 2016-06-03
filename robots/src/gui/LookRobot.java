package gui;


import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class LookRobot extends Observable {

    private volatile double m_robotPositionX = 100;
    private volatile double m_robotPositionY = 100;
    private volatile double m_robotDirection = 0;
    private static final double maxVelocity = 0.1;
    private static final double maxAngularVelocity = 0.001;

    private Observable myObservable = new Observable()
    {
        public void notifyObservers(Object arg) {
            setChanged();
            super.notifyObservers(arg);
        }
    };
    public void addObserver(Observer o)
    {
        myObservable.addObserver(o);
    }



    protected List<Double> getModel(){
        List<Double> model = Arrays.asList(m_robotPositionX, m_robotPositionY, m_robotDirection);
        return  model;
    }
    protected void updateModel(int m_targetPositionX, int m_targetPositionY){
        myObservable.notifyObservers();
        double distance = ComputePhisics.distance(m_targetPositionX, m_targetPositionY,
                m_robotPositionX, m_robotPositionY);
        if (distance < 0.5)
        {
            return;
        }
        double velocity = maxVelocity;
        double angleToTarget = ComputePhisics.angleTo(m_robotPositionX, m_robotPositionY, m_targetPositionX, m_targetPositionY);
        double angularVelocity = 0;
        if (angleToTarget > m_robotDirection)
        {
            angularVelocity = maxAngularVelocity;
        }
        if (angleToTarget < m_robotDirection)
        {
            angularVelocity = -maxAngularVelocity;
        }

        moveRobot(velocity, angularVelocity, 10);
        setChanged();
        notifyObservers();

    }


    private void moveRobot(double velocity, double angularVelocity, double duration)
    {
        velocity = ComputePhisics.applyLimits(velocity, 0, maxVelocity);
        angularVelocity = ComputePhisics.applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        double newX = m_robotPositionX + velocity / angularVelocity *
                (Math.sin(m_robotDirection  + angularVelocity * duration) -
                        Math.sin(m_robotDirection));
        if (!Double.isFinite(newX))
        {
            newX = m_robotPositionX + velocity * duration * Math.cos(m_robotDirection);
        }
        double newY = m_robotPositionY - velocity / angularVelocity *
                (Math.cos(m_robotDirection  + angularVelocity * duration) -
                        Math.cos(m_robotDirection));
        if (!Double.isFinite(newY))
        {
            newY = m_robotPositionY + velocity * duration * Math.sin(m_robotDirection);
        }
        m_robotPositionX = newX;
        m_robotPositionY = newY;
        double newDirection = ComputePhisics.asNormalizedRadians(m_robotDirection + angularVelocity * duration);
        m_robotDirection = newDirection;
    }




}
