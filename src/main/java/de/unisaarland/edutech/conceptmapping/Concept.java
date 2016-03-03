package de.unisaarland.edutech.conceptmapping;

import java.io.Serializable;

public class Concept implements Serializable {

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
			Concept c =  (Concept) super.clone();
			c.name = name.clone();
			return c;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(rotate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Concept other = (Concept) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(rotate) != Double.doubleToLongBits(other.rotate))
			return false;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}
	
	
}
