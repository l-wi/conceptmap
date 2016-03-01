package de.unisaarland.edutech.conceptmapping;

public class Concept {

	private CollaborativeString name;
	private double x;
	private double y;
	private double rotate;

	public Concept(CollaborativeString name) {
		this.name = name;
	}

	public User getOwner() {
		return name.getOwner();
	};

	public CollaborativeString getName() {
		return name;
	}

	@Override
	public String toString() {
		return name.getContent();
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setPosition(double x, double y,double r){
		setX(x);
		setY(y);
		setRotate(r);
	}
	
	private void setX(double x) {
		if (x < 0 || x > 1)
			throw new RuntimeException("x coordinate for concept does not fullfill 0<x<1 x=" + x);
		this.x = x;
	}

	private void setY(double y) {
		if (y < 0 || y > 1)
			throw new RuntimeException("x coordinate for concept does not fullfill 0<y<1 y=" + y);
		this.y = y;
	}

	private void setRotate(double rotate) {
		this.rotate = rotate;
	}
	
	public double getRotate() {
		return rotate;
	}

	@Override
	public Concept clone() {
		try {
			return (Concept) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
}
