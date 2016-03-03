package de.unisaarland.edutech.conceptmapping;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CollaborativeString implements Cloneable, Serializable {

	private class Position implements Serializable {

		public Position(int length, User u) {
			this.length = length;
			this.u = u;
		}

		int length;
		User u;

		@Override
		public String toString() {
			return "Position Length: " + length;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + length;
			result = prime * result + ((u == null) ? 0 : u.hashCode());
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
			Position other = (Position) obj;

			if (length != other.length)
				return false;
			if (u == null) {
				if (other.u != null)
					return false;
			} else if (!u.equals(other.u))
				return false;
			return true;
		}

		private CollaborativeString getOuterType() {
			return CollaborativeString.this;
		}
	}

	private List<Position> positions = new ArrayList<Position>();
	private StringBuilder builder = new StringBuilder();

	private User owner;

	public CollaborativeString(User owner) {
		this(owner, "");
	}

	public CollaborativeString(User owner, String content) {
		this.owner = owner;
		if (content.length() > 0)
			append(owner, content);

	}

	public User getOwner() {
		return owner;
	}

	public int length() {
		return builder.length();
	}

	public CollaborativeString append(User u, String content) {
		return insert(u, builder.length(), content);
	}

	public CollaborativeString insert(User u, int index, String content) {
		Position positionToInsert = new Position(content.length(), u);
		builder.insert(index, content);
		return insertPosition(index, positionToInsert);
	}

	private CollaborativeString insertPosition(int index, Position positionToInsert) {

		int currentEndingIndex = 0;

		for (int i = 0; i < positions.size(); i++) {
			Position p = positions.get(i);

			currentEndingIndex += p.length;

			if (currentEndingIndex > index) {
				return splitAndInsert(index, positionToInsert, currentEndingIndex, i, p);
			} else if (index == currentEndingIndex) {
				return insertBetweenPositions(index, positionToInsert, i);
			}

		}
		positions.add(positionToInsert);

		return this;
	}

	private CollaborativeString insertBetweenPositions(int index, Position positionToInsert, int i) {
		positions.add(i + 1, positionToInsert);
		return this;
	}

	private CollaborativeString splitAndInsert(int insertionIndex, Position pNew, int contentLength, int i,
			Position positionToSplit) {
		Position pLeft = null;
		int charsLeftOfInsertionPoint = insertionIndex - (contentLength - positionToSplit.length);
		if (charsLeftOfInsertionPoint > 0) {
			pLeft = new Position(charsLeftOfInsertionPoint, positionToSplit.u);
		}

		Position pRight = positionToSplit;
		pRight.length = pRight.length - charsLeftOfInsertionPoint;

		positions.add(i, pNew);
		if (pLeft != null)
			positions.add(i, pLeft);
		return this;
	}

	public CollaborativeString remove(int startIndex, int count) {
		builder.delete(startIndex, startIndex + count);

		int intervalStart = 0;
		boolean isSameP = true;
		boolean doDelete = false;

		for (int i = 0; i < positions.size(); i++) {
			Position p = positions.get(i);

			int intervalEnd = intervalStart + p.length;

			// left interval start found
			if (!doDelete && startIndex < intervalEnd) {
				int remainingLeft = startIndex - intervalStart;
				p.length = remainingLeft;
				doDelete = true;
			}
			// right interval end found
			if (startIndex + count <= intervalEnd) {
				int remainingRight = intervalEnd - (startIndex + count);
				// in case we remove a subinterval of a larger interval
				p.length = (isSameP) ? p.length + remainingRight : remainingRight;

				if (p.length == 0)
					positions.remove(i);

				return this;
			}
			if (doDelete) {
				positions.remove(i);
				i--;
			}
			intervalStart = intervalEnd;
			isSameP = false;
		}
		return this;
	}

	public CollaborativeString removeLast(int count) {
		return remove(length() - count, count);
	}

	public String getContent() {
		return builder.toString();
	}

	public String toDebugString() {
		StringBuilder debugBuilder = new StringBuilder();
		int startInverval = 0;
		int endInterval = 0;

		for (Position p : positions) {

			endInterval += p.length;
			String openTag = "<" + p.u.getName() + ">";
			String closeTag = "</" + p.u.getName() + ">";

			CharSequence part = builder.subSequence(startInverval, endInterval);

			debugBuilder.append(openTag).append(part).append(closeTag);

			startInverval = endInterval;
		}

		return debugBuilder.toString();
	}

	@Override
	public CollaborativeString clone() {
		CollaborativeString s;
		try {
			s = (CollaborativeString) super.clone();

			s.builder = new StringBuilder(this.builder.toString());
			s.positions = new ArrayList<>();

			for (Position p : this.positions)
				s.positions.add(new Position(p.length, p.u));

			return s;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((builder == null) ? 0 : builder.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((positions == null) ? 0 : positions.hashCode());
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
		CollaborativeString other = (CollaborativeString) obj;
		if (builder == null) {
			if (other.builder != null)
				return false;
		} else if (!builder.toString().equals(other.builder.toString()))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (positions == null) {
			if (other.positions != null)
				return false;
		} else if (!positions.equals(other.positions))
			return false;
		return true;
	}

}
