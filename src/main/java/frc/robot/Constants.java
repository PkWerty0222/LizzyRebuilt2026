package frc.robot;

public final class Constants {
    
    public static final class ClimbConstants{
        public static final int motorClimb1ID = 16;
        

    }
    public static final class IntakeConstants{
        public static final int rotateIntakeMotorId = 18;  
        public static final int collectIntakeMotorId = 21;
        public static final double intakeKP = .2;
        public static final double intakeKi = 0;
        public static final double intakeKd = .08;
        public static final double intakePositionIn = -11;
        public static final double intakePositionOut = -29;
        
    }
    public static final class FeederConstants{
        public static final int motorFeeder1ID = 20;
    }
    public static final class IndexerConstants{
       
        public static final int motorIndexer1ID = 17;
    }
    public static final class ShooterConstants{
        public static final int motorShooter1ID = 19;
        /** Angle of the Shooter */
        public final static double angleOfShooter = 49;
        /** Height between Shooter and Hub */
        public final static double height = 1;
        /** Radius of Shooter Wheel */
        public final static double radiusOfWheel = .2;

    }
    public static final class SwerveConstants{
        public static final double speedP = -.4;
    }


}
