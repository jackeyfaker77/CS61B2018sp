public class NBody {
    public static double readRadius(String filename) //读取本文上的前2行:行星数量和宇宙半径
    {
        In in = new In(filename);
        int num = in.readInt();
        double Radius = in.readDouble();
        return Radius;
    }
    public static Planet[] readPlanets(String filename)
    {////为文本中的5行重复数据类型制造一个Planet数组(此时未new),并制造for循环一一读取他们的数据,再把这些数据丢进new出的planet类数组里的每一个索引类里的对应成员数据
        In in = new In(filename);
        int num = in.readInt();
        double Radius = in.readDouble();
        Planet[] planets = new Planet[num];//
        for (int i = 0; i < planets.length ; i++) {
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String imgFileName = in.readString();
            planets[i] =new Planet(xxPos,yyPos,xxVel,yyVel,mass,imgFileName);
        }//new出一个planet类数组,并把他赋值给之前做好的一个空数组planets
        return planets;
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("请提供命令行参数：T dt filename");
            return;
        }
        double T = Double.parseDouble(args[0]);//记得在命令行中传数据给T
        double dt = Double.parseDouble(args[1]);//记得在命令行中传数据给dt
        String filename = args[2];//给把命令行里的第二个索引(里面的数据是打命令行时传过去的文本)赋值给filename变量
        StdAudio.play("audio/2001.mid");
        double Radius = readRadius(filename);//把filename变量传给readradius方法,并调用readRadius方法,此时会读取文本里的前2行,前2行数据赋值给radius
        Planet[] planets = readPlanets(filename);//调用readRadius方法,读取每一个星球的数据,再赋值给planet数组类型的planets,每一个索引对应着每个星球的位置半径等等
        StdDraw.setScale(-Radius, Radius);     //设置xy的范围
        StdDraw.enableDoubleBuffering();
        //StdDraw.picture(0, 0, "images/starfield.jpg");//在一个窗口的原点中填充给定的背景图
       // for (Planet planet:planets){//new出一个数据类型为Planet类的变量,名字为planet,把数组名为planets的planet类数组里的每一个索引一一传给planet
         //   planet.draw();//每次传就调用planet类中的draw方法,一一根据坐标和图片名字把图片合适的画在一个窗口里
        //}
        //注意此时变量planets包含了所有需要的星球,以及他们的位置 半径 质量 速度.planets是一个数组
        double t = 0;
        int num = planets.length;
        while(t <= T)
        {
            double[] xForces = new double[num];
            double[] yForces = new double[num];
            for(int i = 0; i < num; i++){
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for(int i = 0; i < num; i++){
                planets[i].update(dt, xForces[i], yForces[i]);
            }
            // draw the backgroud picture
            StdDraw.picture(0, 0, "images/starfield.jpg");
            // draw all the planets
            for (Planet planet : planets) {
                planet.draw();
            }

            StdDraw.show();
            StdDraw.pause(10);
            t = t + dt;
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", Radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}
