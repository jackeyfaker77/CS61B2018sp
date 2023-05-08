public class Planet {
    public double xxPos,yyPos,xxVel,yyVel,mass;//x位置,y位置,x方向上的速度,y方向上的速度,质量
    public String imgFileName;//图片名称
    private static final double G = 6.67e-11;

    public Planet(double xxPos, double yyPos, double xxVel, double yyVel, double mass, String imgFileName) {
        this.xxPos = xxPos;
        this.yyPos = yyPos;
        this.xxVel = xxVel;
        this.yyVel = yyVel;
        this.mass = mass;
        this.imgFileName = imgFileName;
    }
    // 此处的有些变量的类型可能是int,检错注意

    public Planet(Planet p) {
        this.xxPos = 1.0;
        this.yyPos = 2.0;
        this.xxVel = 3.0;
        this.yyVel = 4.0;
        this.mass = 5.0;
        this.imgFileName = "jupiter.gif";
    }
    /*public double calcDistance(Planet p) //计算两行星的距离r
    {
        double dx = p.xxPos - xxPos;//xxPos是指最后一次实例时的x坐标,而p.xxPos则是参数p所指向的Planet类的实例的x坐标。
        double dy = p.yyPos - yyPos;//同理
        double r = dx*dx+dy*dy;
        return r;
    }*/
    public double calcDistance(Planet p){
        return Math.sqrt((xxPos - p.xxPos)*(xxPos - p.xxPos)
                + (yyPos - p.yyPos)*(yyPos - p.yyPos));
    }
    public double calcForceExertedBy(Planet P) //计算相对作用力F
    {                               
        double r = calcDistance(P);
        double F = (G*P.mass*mass)/(r*r);
        return F;
    }
    public double calcForceExertedByX(Planet P) //计算F在x方向上的力:
    {
        double dx = P.xxPos - xxPos;
        double F = calcForceExertedBy(P);
        double r = calcDistance(P);
        double Fx =F*dx/r;
        return Fx;
    }
    public double calcForceExertedByY(Planet P)//计算F在y方向上的力:
    {
        double dy = P.yyPos - yyPos;
        double F = calcForceExertedBy(P);
        double r = calcDistance(P);
        double Fy =F*dy/r;
        return Fy;
    }
    public double calcNetForceExertedByX(Planet[] allplanets) //计算星球受到在X方向上受到除他以外的星球的合力F
    {
        double totalForce = 0;
        for(Planet planet : allplanets){
         if (this == planet){
             continue;
         }
         totalForce = totalForce +calcForceExertedByX(planet);
        }
        return totalForce;
    }
    public double calcNetForceExertedByY(Planet[] allPlanets)//计算星球受到在Y方向上受到除他以外的星球的合力F
    {
        double totalForce = 0;
        for (Planet planet : allPlanets) {
            if (this == planet) {
                continue;
            }
            totalForce += calcForceExertedByY(planet);
        }
        return totalForce;
    }
    public void update(double dt,double fX,double fY) //当星球移动时的他的位置变化
    {
        double ax = fX/mass;
        double ay = fY/mass;
        xxVel += dt * ax;
        yyVel += dt * ay;
        xxPos += xxVel * dt;
        yyPos += yyVel * dt;
    }
    public void draw()
    {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }//根据给出的XY坐标以及图片名字,把图片合适的画在一个窗口里
}
