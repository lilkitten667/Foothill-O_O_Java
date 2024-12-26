import becker.robots.*;
import java.util.Random;

public class A21 extends Object
{
    // CustomRobot class with reusable methods
    public static class CustomRobot extends Robot
    {
        public CustomRobot(City city, int street, int avenue, Direction dir, int numThings)
        {
            super(city, street, avenue, dir, numThings);
        }

        int counter = 0; // universal variable.

        public boolean canPutThing()
        /**
         * This method checks to see if there are less or equal to 5 things in the
         * backpack if so put the thing the move once the if statement becomes no longer
         * valid and method gets a return of false.
         */
        {

            if (countThingsInBackpack() <= counter)
            {
                this.putThing();
                this.move();
                counter++;
                System.out.print(counter);
            }
            return false;
        }

        public void moveToWall()
        /**
         * This method takes advantage of of the boolean method saying as long as the
         * front is clear we move in the same direction, In this case it is a wall
         */
        {
            while (frontIsClear())
            {
                this.move();
            }
        }

        public void planter()
        /**
         * This method is a nested while loop that has the first boolean condition set
         * to false, asking if the robot cannot pick a thing, and the count is higher or
         * equal to zero the putThing x4 and the move x4 methods are called. Calling the
         * counter and turning the robot around at the end of the row.
         */
        {

            while (!canPickThing())
            {

                if (countThingsInBackpack() >= 0)
                {
                    this.putThing();
                    this.move();
                    this.putThing();
                    this.move();
                    this.putThing();
                    this.move();
                    this.putThing();
                    counter++;
                    this.turnAround();

                }
            }
        }

        public void turnAround()
        /** This method simply turns the robot 180 degrees */
        {
            this.turnLeft();
            this.turnLeft();
        }

        public void turnRight()
        /**
         * This method uses the above method and turn left to get the opposing to left
         * turn
         */
        {
            this.turnAround();
            this.turnLeft();
        }

        public void nextRow()
        /**
         * This method turns the robot down to the next row by using nested ifs... first
         * check is the direction second check is avenue location set in map coordinates
         * if both are met then the robot turns left, moves down the next intersection
         * turns left again.
         */
        {
            if (getDirection() != Direction.SOUTH)
            {
                if (getAvenue() == 2)
                {
                    this.turnLeft();
                    this.move();
                    this.turnLeft();

                }
                this.move();
            }
        }

        public void returnToStart()
        /**
         * This method moves returns the robot to the starting place and direction,
         * required East.
         */
        {
            this.moveToWall();
            this.turnRight();
            this.moveToWall();
            this.turnRight();
        }

        public void doEverything()
        /**
         * This method is the whole operation I created a boolean variable set it to
         * false and started my while loop with the opposing of my variable, nested a
         * while not done loop i said if the front is clear i am going to have the robot
         * move to the wall pick up a thing turn around move to the wall again then next
         * row. called counter add it by one and then next nested if street gets to 5 we
         * are to plant objects then once the back pack is empty return to start and
         * done is set to true
         */
        {
            boolean done = false;

            while (!done)
            {
                if (frontIsClear())
                {
                    this.moveToWall();
                    this.pickThing();
                    this.turnAround();
                    this.moveToWall();
                    this.nextRow();
                    counter++;
                    if (getStreet() == 5)
                    {
                        this.planter();

                        if (countThingsInBackpack() < counter)
                        {
                            this.returnToStart();

                        }
                        done = true;
                    }
                }
            }
        }

        public static void main(String[] args)

        {
            City wallville = new City(6, 12);
            CustomRobot rob = new CustomRobot(wallville, 1, 2, Direction.EAST, 0);
            A21.buildCity(wallville); // Build the city layout
            rob.doEverything(); // sending robot through the "city"
        }

    }

    /////////////////////////////////////////////////////////////////////////////////////////
    // No need to touch any of the code below.
    // All it does is construct the maze in the city.
    /////////////////////////////////////////////////////////////////////////////////////////
    public static void buildCity(City wallville)
    {
        // Width and height must be at least 2 (each)
        // Feel free to change these numbers, and see how your race track changes

        Random randomNumberGenerator = new Random();
        int top = 1;
        int left = 2;
        int height = 4;
        int width = 4 + randomNumberGenerator.nextInt(7);

        int streetNumber = top;
        while (streetNumber <= height)
        {
            if (streetNumber == 1)
            {
                // the topmost line:
                new Wall(wallville, streetNumber, left, Direction.NORTH);
            } else if (streetNumber == height)
            {
                // generate the 'holding spot' thing at the bottom: the corner:
                new Wall(wallville, streetNumber + 1, left, Direction.WEST);
                new Wall(wallville, streetNumber + 1, left, Direction.SOUTH);
                int spotNum = left + 1;
                int counter = 0;
                while (counter < height)
                {
                    new Wall(wallville, streetNumber + 1, spotNum, Direction.NORTH);
                    new Wall(wallville, streetNumber + 1, spotNum, Direction.SOUTH);
                    // Uncomment the next line for a 'final state' picture (i.e., the second picture
                    // in the assignment)
                    // new Thing(wallville, streetNumber + 1, spotNum);
                    ++spotNum;
                    ++counter;
                }
                new Wall(wallville, streetNumber + 1, spotNum, Direction.WEST);
            }

            // the most western, vertical line:
            new Wall(wallville, streetNumber, left, Direction.WEST);
            // the most eastern, vertical line:
            new Wall(wallville, streetNumber, width, Direction.EAST);
            // the Thing at the end of the tunnel
            new Thing(wallville, streetNumber, width);

            int aveNum = left + 1;
            while (aveNum <= width)
            {
                new Wall(wallville, streetNumber, aveNum, Direction.NORTH);
                new Wall(wallville, streetNumber, aveNum, Direction.SOUTH);
                ++aveNum;
            }

            ++streetNumber;
        }
    }

}
