package br.edu.unisinos.pgr.lcenteleghe.graphics;

/**
 * This class represents the RGB color model.
 * @author Luis Gustavo
 *
 */
public class RGB {
	private int r;
	private int g;
	private int b;
	private int a;

	public RGB(int r, int g, int b) {
		super();
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = 255;
	}

	public RGB(int r, int g, int b, int a) {
		super();
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public RGB() {
		this.a = 255;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public int getA() {
		return a;
	}
	
	public float getNormalizedA() {
		return a/255f;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int wrapInInt() {
		return (getA() << 24 | getR() << 16) | (getG() << 8) | (getB());
	}
	
	public static int wrapInInt(int a, int r, int g, int b) {
		return (a << 24 | r << 16) | (g << 8) | (b);
	}
	
	public static int wrapInInt(int r, int g, int b) {
		return (255 << 24 | r << 16) | (g << 8) | (b);
	}


	private static final double RGBCubeMaxLength = Math.sqrt(194075);

	public static double calculateAbsoluteDistance(RGB c, RGB o) {
		return Math.sqrt(Math.pow(c.getR() - o.getR(), 2)
				+ Math.pow(c.getG() - o.getG(), 2)
				+ Math.pow(c.getB() - o.getB(), 2));
	}

	public static double calculateRelativeDistance(RGB c, RGB o) {
		return calculateAbsoluteDistance(c, o) / RGBCubeMaxLength;
	}

	public static RGB getRandomRGB() {
		return new RGB(grn(), grn(), grn());
	}

	private static int grn() {
		return (int) (Math.random() * 255);
	}

	@Override
	public String toString() {
		return "RGB [r=" + r + ", g=" + g + ", b=" + b + "]";
	}

	public static RGB getRGBFromWrappedInt(int wrappedInt) {
		return new RGB((wrappedInt >> 16) & 0xff, (wrappedInt >> 8) & 0xff,
				wrappedInt & 0xff, (wrappedInt >> 24) & 0xff);
	}
}
