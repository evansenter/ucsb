//package edu.csusb.danby.math;


import java.lang.Math;


/**
 *  Mathematical functions for statistics. Most methods translated from fortran
 *  source in statlib
 *
 * @version   Sun Jul 28 10:08:05 PDT 2002
 */
public class ProbMath extends java.lang.Object {

    /*
     *     DOUBLE PRECISION FUNCTION DUMNOR(X)
     *
     *
     *                              Function
     *
     *
     *     Computes the cumulative  of    the  normal   distribution,   i.e.,
     *     the integral from -infinity to x of
     *          (1/sqrt(2*pi)) exp(-u*u/2) du
     *
     *
     *                              Method
     *
     *
     *     The    rational function  approximation   from pages   90  - 92 of
     *     Kennedy  and Gentle,  Statistical  Computing,  Marcel  Dekker,  NY
     *     1980.
     *
     *
     *                              Arguments
     *
     *
     *     X --> Argument at which cumulative normal is evaluated
     *                    DOUBLE PRECISION X
     *
     * @param  x      Description of Parameter
     * @return        Description of the Returned Value
     */

    /**
     * normalCdf calculates the cummulative (standard) normal
     * distribution function of x
     *
     * @param  x  input value
     * @return    P(Z,z);
     */
    public static double normalCdf(double x) {
        double z;
        double z2;
        double zm2;
        double derf = 0;
        //Keep the compiler happy ?
        double derfc = 0;
        // keep the compiler happy ?
        boolean qdirct;
        double dumnor;//return value
 
        double[] xnum1 = {2.4266795523053175E2, 2.1979261618294152E1,
                6.9963834886191355E0, -3.5609843701815385E-2};
        double[] xden1 = {2.1505887586986120E2, 9.1164905404514901E1,
                1.5082797630407787E1, 1.0000000000000000E0};
        double[] xnum2 = {3.004592610201616005E2, 4.519189537118729422E2,
                3.393208167343436870E2, 1.529892850469404039E2,
                4.316222722205673530E1, 7.211758250883093659E0,
                5.641955174789739711E-1, -1.368648573827167067E-7};
        double[] xden2 = {3.004592609569832933E2, 7.909509253278980272E2,
                9.313540948506096211E2, 6.389802644656311665E2,
                2.775854447439876434E2, 7.700015293522947295E1,
                1.278272731962942351E1, 1.000000000000000000E0};
        double[] xnum3 = {-2.99610707703542174E-3, -4.94730910623250734E-2,
                -2.26956593539686930E-1, -2.78661308609647788E-1,
                -2.23192459734184686E-2};
        double[] xden3 = {1.06209230528467918E-2, 1.91308926107829841E-1,
                1.05167510706793207E0, 1.98733201817135256E0,
                1.00000000000000000E0};
        double pim12 = 0.5641895835477562869480795E0;
        double sqrt2 = 1.4142135623730950488E0;

        if ((Math.abs(x) < 1.0E-30)) {
            dumnor = 0.5;
        } else if (x < -38.0) {
            dumnor = 0.0;
        } else if (x < -15.0) {
            dumnor = Math.exp(dlanor(x));
        } else if (x > 6.0) {
            dumnor = 1.0;
        }

        z = Math.abs(x / sqrt2);
        z2 = z * z;
        zm2 = 1.0E0 / z2;
        if (z < 0.5E0) {
            derf = z * devlpl(xnum1, 4, z2) / devlpl(xden1, 4, z2);
            qdirct = true;
        } else if (z < 4.0E0) {
            derfc = Math.exp(-z2) * devlpl(xnum2, 8, z) / devlpl(xden2, 8, z);
            qdirct = false;
        } else {
            derfc = (Math.exp(-z2) / z) * (pim12 + zm2 * devlpl(xnum3, 5, zm2) / devlpl(xden3, 5, zm2));
            qdirct = false;
        }
        if (x >= 0.0) {
            if (!(qdirct)) {
                derf = 1.0E0 - derfc;
            }
            dumnor = (1.0E0 + derf) / 2.0E0;
        } else {
            if (qdirct) {
                derfc = 1.0E0 - derf;
            }
            dumnor = derfc / 2.0E0;
        }
        return dumnor;
    }


    /*
     *  devpl: Double precision EVALuate a PoLynomial at X
     *  translated from Fortran source in statlib
     *
     *  @param A is the array of coefficients
     *  @param n is the length of A
     *  @param x is the point at which P is to be evaluated
     */

    static double devlpl(double[] A, int n, double x) {
        double term;

        term = A[n - 1];
        // coef for java are A[0] to A[n-1]
        for (int i = n - 2; i >= 0; i--) {
            term = A[i] + term * x;
        }
        return term;
    }


    /*
     *  dlanor : Double precision Logarith of the Asymptotic Normal
     *
     *
     *
     */

    static double dlanor(double x) {
        double result;
        double dlsqpi = 0.91893853320467274177E0;
        double approx;
        double correc;
        double xx;
        double xx2;
        double[] coef = {-1.0E0, 3.0E0, -15.0E0, 105.0E0, -945.0E0, 10395.0E0,
                -135135.0E0, 2027025.0E0, -34459425.0E0, 654729075.0E0,
                -13749310575E0, 316234143225.0E0};

        xx = Math.abs(x);

//      IF (xx.LT.5.0D0) STOP ' Argument too small in DLANOR'
// SHOULD THROW EXCEPTION
        approx = -dlsqpi - 0.5 * xx * xx - Math.log(xx);
        xx2 = xx * xx;
        correc = devlpl(coef, 12, 1.0E0 / xx2) / xx2;
        correc = dln1px(correc);

        result = approx + correc;

        return result;

    }


    /**
     * dln1px asymptotic evaluation of ln(1+a)
     *
     * @param  a  argument of function
     * @return    approximation of ln(1+a)
     */
    static double dln1px(double a) {
        double result;
        double t;
        double t2;
        double w;
        double x;
        double p1 = -.129418923021993E+01;
        double p2 = 0.405303492862024E+00;
        double p3 = -.178874546012214E-01;
        double q1 = -.162752256355323E+01;
        double q2 = 0.747811014037616E+00;
        double q3 = -.845104217945565E-01;

        if (Math.abs(a) <= 0.375) {
            t = a / (a + 2.0);
            t2 = t * t;
            w = (((p3 * t2 + p2) * t2 + p1) * t2 + 1.0) / (((q3 * t2 + q2) * t2 + q1) * t2 + 1.0);
            result = 2.0 * t * w;
            return result;
        } else {
            x = (double) (1.0 + a);
            return Math.log(x);
        }
    }


    /**
     * inverseNormal
     *
     * @param  p  argument of function
     * @return    z such that P(Z > z)= p.
     */

    public static double inverseNormal(double p) {
        double y;
        double z;
        double invnor;
        double sign = 1.0;
        double[] xnum = {-0.322232431088E0, -1.000000000000E0,
                -0.342242088547E0, -0.204231210245E-1,
                -0.453642210148E-4};
        double[] xden = {0.993484626060E-1, 0.588581570495E0,
                0.531103462366E0, 0.103537752850E0,
                0.38560700634E-2};
        if (p < 1.0E-20) {
            invnor = -10.0;
            return invnor;
        } else if (p > 1.0) {
            // IMPOSSIBLE ?
            invnor = 10.0;
            return invnor;
        } else if (p <= 0.50) {
            sign = -1.0;
            z = p;
        } else {
            z = 1.0 - p;
        }
        y = Math.sqrt(-2.0 * Math.log(z));
        invnor = y + devlpl(xnum, 5, y) / devlpl(xden, 5, y);
        invnor = sign * invnor;
        return invnor;
    }

public static double factorial(int n)  { // would overflow int too soon!
    int nx=1;
    double x=-999.0;
    if (n<0) { 
            throw new IllegalArgumentException("n must be nonnegative");
            
    }    
    // if n < 12 calculate exactly
    if (n<12) { 
        for (int j=1; j<= n; j++) {nx *= j;}
        x= (double)nx;
    }
    if (n >= 12) {x= Math.exp(loggamma((double)n+1.0));}
    return x;
    }


/**
* log gamma function
* @param x is the value at which log gamma is to be returned
*/

public static double loggamma(double a) {



/*C                              Method
C
C
C     Renames GAMLN from:
C     DiDinato, A. R. and Morris,  A.   H.  Algorithm 708: Significant
C     Digit Computation of the Incomplete  Beta  Function Ratios.  ACM
C     Trans. Math.  Softw. 18 (1993), 360-373.
C
C**********************************************************************
C-----------------------------------------------------------------------
C            EVALUATION OF LN(GAMMA(A)) FOR POSITIVE A
C-----------------------------------------------------------------------
C     WRITTEN BY ALFRED H. MORRIS
C          NAVAL SURFACE WARFARE CENTER
C          DAHLGREN, VIRGINIA
*/
//--------------------------
//     D = 0.5*(LN(2*PI) - 1)
//--------------------------
//    .. Scalar Arguments ..
    double c0 = .833333333333333E-01;
    double c1 = -.277777777760991E-02;
    double c2 =  .793650666825390E-03;
    double c3 = -.595202931351870E-03;
    double c4 = .837308034031215E-03;
    double c5 = -.165322962780713E-02;
    double d = .418938533204673E0;
    double t;
    double w;
    double dlngam;  //return value
     int n; 
      if (a <= 0.8) {
      dlngam = gamln1(a) - Math.log(a);
        }
    else  if (a <= 2.250) {
        t = (a-0.5) - 0.5;
        dlngam = gamln1(t);
            }
    else if (a < 10.0) {
            n = (int)(a - 1.25);
            t = a;
        w = 1.00;
      for (int i = 1; i <= n; i++) {
          t = t - 1.0;
          w = t*w;
    }
   
      dlngam = gamln1(t-1.00) + Math.log(w);
        }
    else {
        t = (1.00/a)*(1.00/a);
        w = (((((c5*t+c4)*t+c3)*t+c2)*t+c1)*t+c0)/a;
      dlngam = (d+w) + (a-0.50)* (Math.log(a)-1.00);
      }
    return dlngam;
}


     static double  gamln1(double a) {
//-----------------------------------------------------------------------
//     EVALUATION OF LN(GAMMA(1 + A)) FOR -0.2 .LE. A .LE. 1.25
//-----------------------------------------------------------------------
//     .. Scalar Arguments ..
    double w,x, result;
    double  p0 =.577215664901533E+00;
    double p1=.844203922187225E+00;
    double p2 = -.168860593646662E+00;
    double p3 = -.780427615533591E+00;
    double p4 = -.402055799310489E+00;
    double p5 = -.673562214325671E-01;
    double p6 = -.271935708322958E-02;
    double q1 = .288743195473681E+01;
    double q2 = .312755088914843E+01;
    double q3 = .156875193295039E+01;
    double q4 = .361951990101499E+00;
    double q5 = .325038868253937E-01;
    double q6 = .667465618796164E-03;
    double r0 = .422784335098467E+00;
    double r1 = .848044614534529E+00;
    double r2 = .565221050691933E+00;
    double r3 = .156513060486551E+00;
    double r4 = .170502484022650E-01;
    double r5 =  .497958207639485E-03;
    double s1 =  .124313399877507E+01;
    double s2 =  .548042109832463E+00;
    double s3 =  .101552187439830E+00;
    double s4 =  .713309612391000E-02;
    double s5 =  .116165475989616E-03;

      if (a < 0.6) {
      w = ((((((p6*a+p5)*a+p4)*a+p3)*a+p2)*a+p1)*a+p0)/((((((q6*a+  q5)*a+q4)*a+q3)*a+q2)*a+q1)*a+1.0);
      result = -a*w;
    }
    else {
        x = (a-0.5) - 0.5;
        w = (((((r5*x+r4)*x+r3)*x+r2)*x+r1)*x+r0)/( 	((((s5*x+s4)*x+s3)*x+s2)*x+s1)*x+1.00);
      result = x*w;
    }
      return result;
}  				//END of gamln1      


    /**
    * Calculate N choose k
    */
    public static double combin( int N, int k) {
        double result;
        result = Math.exp( loggamma(N+1) - loggamma(k+1) - loggamma(N-k+1));
        return result;
        }
            
    
    
}

