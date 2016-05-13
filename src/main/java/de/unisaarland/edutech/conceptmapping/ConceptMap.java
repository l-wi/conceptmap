package de.unisaarland.edutech.conceptmapping;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ConceptMap implements Cloneable, Serializable {

	private static final int LIST_INITIAL_CAPACITY = 64;

	private Experiment experiment;
	private List<Concept> concepts;
	private Link[][] adjacencyMatrix;

	private LinkFactory linkFactory;

	public ConceptMap(Experiment experiment) {
		this(experiment, new DefaultLinkFactory());

	}

	public ConceptMap(Experiment experiment, LinkFactory factory) {
		this(experiment, LIST_INITIAL_CAPACITY, factory);

	}

	public ConceptMap(Experiment experiment, int conceptCapacity, LinkFactory factory) {
		this.experiment = experiment;
		this.concepts = new ArrayList<Concept>(conceptCapacity);
		this.linkFactory = factory;
		this.adjacencyMatrix = new Link[conceptCapacity][conceptCapacity];
	}

	public int addConcept(Concept c) {
		concepts.add(c);
		return concepts.size() - 1;
	}

	public Concept getConcept(int index) {
		return concepts.get(index);
	}

	public int indexOf(Concept c) {
		return concepts.indexOf(c);
	}

	public boolean isLinkedDirected(Concept a, Concept b) {
		return isLinkedDirectedStartToEnd(indexOf(a), indexOf(b));
	}

	public boolean isLinkedUndirected(Concept a, Concept b) {
		return isLinkedUndirected(indexOf(a), indexOf(b));
	}

	public boolean isLinkedDirectedStartToEnd(int i, int j) {
		return adjacencyMatrix[i][j] != null && adjacencyMatrix[j][i] == null;
	}

	public boolean isLinkedUndirected(int i, int j) {
		return adjacencyMatrix[i][j] != null && adjacencyMatrix[j][i] != null;
	}

	public Link addDirectedLink(Concept a, Concept b) {
		int i = indexOf(a);
		int j = indexOf(b);

		return addDirectedLink(i, j);
	}

	public Link addDirectedLink(int firstConceptIndex, int secondConceptIndex) {
		throwIfOutOfRange(firstConceptIndex, secondConceptIndex);
		Concept a = concepts.get(firstConceptIndex);
		Concept b = concepts.get(secondConceptIndex);
		Link link = linkFactory.create(a.getOwner(), b.getOwner());
		adjacencyMatrix[firstConceptIndex][secondConceptIndex] = link;
		return link;
	}

	private void throwIfOutOfRange(int firstConceptIndex, int secondConceptIndex) {
		if (firstConceptIndex >= concepts.size() || secondConceptIndex >= concepts.size())
			throw new RuntimeException("Access to indicies " + firstConceptIndex + " | " + secondConceptIndex
					+ " although we have only " + concepts.size() + " concepts!");
	}

	public Link addUndirectedLink(Concept a, Concept b) {
		return addUndirectedLink(indexOf(a), indexOf(b));
	}

	public Link addUndirectedLink(int a, int b) {
		Link l = addDirectedLink(a, b);
		adjacencyMatrix[b][a] = l;
		return l;
	}

	public void removeConcept(Concept c) {
		// idea: swap deleted concept with last concept. Than nullify last.
		int lastIndex = concepts.size() - 1;

		int index = indexOf(c);

		Concept conceptToSwap = concepts.get(lastIndex);
		concepts.set(index, conceptToSwap);

		for (int i = 0; i < concepts.size(); i++) {
			adjacencyMatrix[i][index] = adjacencyMatrix[i][lastIndex];
			adjacencyMatrix[index][i] = adjacencyMatrix[lastIndex][i];
			adjacencyMatrix[i][lastIndex] = null;
			adjacencyMatrix[lastIndex][i] = null;
		}

		concepts.remove(lastIndex);
	}

	public void removeDirectedLink(Concept a, Concept b) {
		int i = indexOf(a);
		int j = indexOf(b);

		adjacencyMatrix[i][j] = null;
	}

	public void removeUndirectedLink(Concept a, Concept b) {
		removeDirectedLink(a, b);
		removeDirectedLink(b, a);
	}

	public Link getLink(Concept a, Concept b) {
		int i = indexOf(a);
		int j = indexOf(b);

		return getLink(i, j);
	}

	public int getConceptCount() {
		return concepts.size();
	}

	public Experiment getExperiment() {
		return experiment;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		for (int row = -1; row < concepts.size(); row++) {
			if (row < 0)
				builder.append("\t\t");
			else
				builder.append(concepts.get(row) + ":\t\t");
			for (int col = 0; col < concepts.size(); col++) {
				if (row < 0)
					builder.append(concepts.get(col) + ":\t\t");
				else
					builder.append(((adjacencyMatrix[row][col] != null) ? "Link" : null) + "\t\t");
			}
			builder.append("\n");
		}

		return (builder.toString().trim().equals("")) ? "- empty concept map -" : builder.toString();

	}

	public void setDirectedRelationToUndirected(Concept start, Concept end) {
		int startIndex = indexOf(start);
		int endIndex = indexOf(end);

		Link l1 = adjacencyMatrix[startIndex][endIndex];
		if (l1 == null)
			adjacencyMatrix[startIndex][endIndex] = adjacencyMatrix[endIndex][startIndex];
		else
			adjacencyMatrix[endIndex][startIndex] = l1;

	}

	public boolean isAnyLinkExisting(Concept concept, Concept concept2) {
		int a = indexOf(concept);
		int b = indexOf(concept2);

		return isAnyLinkExisting(a, b);
	}

	private boolean isAnyLinkExisting(int indexA, int indexB) {
		return !(adjacencyMatrix[indexA][indexB] == null && adjacencyMatrix[indexB][indexA] == null);
	}

	public Link getLink(int i, int j) {
		return adjacencyMatrix[i][j];

	}

	public void clear() {

		for (int i = 0; i < concepts.size(); i++) {
			for (int j = 0; i < concepts.size(); j++) {
				adjacencyMatrix[i][j] = null;
			}
		}

		concepts.clear();

	}

	@Override
	public ConceptMap clone() {

		// focus Question
		FocusQuestion oldQuestion = experiment.getFocusQuestion();
		User creatorClone = oldQuestion.getCreator();
		Date creationDateClone = new Date(oldQuestion.getCreationDate().getTime());
		FocusQuestion questionClone = new FocusQuestion(oldQuestion.getQuestion(), creatorClone, creationDateClone);

		// experiment
		User researcherClone = this.experiment.getReseacher();
		Date runDateClone = new Date(this.experiment.getRunDate().getTime());
		Experiment experimentClone = new Experiment(researcherClone, questionClone, runDateClone, 4,
				this.experiment.USE_AWT);

		experiment.getParticipants().forEach((v) -> experimentClone.addParticipant(v));

		ConceptMap map;

		try {
			map = (ConceptMap) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}

		map.experiment = experimentClone;
		map.adjacencyMatrix = new Link[this.adjacencyMatrix.length][this.adjacencyMatrix.length];
		map.concepts = new ArrayList<>();

		// concepts
		for (Concept c : concepts) {
			map.concepts.add(c.clone());
		}

		// adjacency matrix
		for (int i = 0; i < adjacencyMatrix.length; i++) {
			for (int j = 0; j < adjacencyMatrix[i].length; j++) {
				Link l = adjacencyMatrix[i][j];
				if (l != null) {
					User u1 = l.getFirstUser();
					User u2 = l.getSecondUser();

					CollaborativeString captionClone = l.getCaption().clone();
					map.adjacencyMatrix[i][j] = new Link(u1, u2, captionClone);
				} else
					map.adjacencyMatrix[i][j] = null;
			}
		}

		return map;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(adjacencyMatrix);
		result = prime * result + ((concepts == null) ? 0 : concepts.hashCode());
		result = prime * result + ((experiment == null) ? 0 : experiment.hashCode());
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
		ConceptMap other = (ConceptMap) obj;
		if (!Arrays.deepEquals(adjacencyMatrix, other.adjacencyMatrix))
			return false;
		if (concepts == null) {
			if (other.concepts != null)
				return false;
		} else if (!concepts.equals(other.concepts))
			return false;
		if (experiment == null) {
			if (other.experiment != null)
				return false;
		} else if (!experiment.equals(other.experiment))
			return false;
		return true;
	}

	public int getIngoingLinkCount(Concept c) {
		int index = indexOf(c);
		if (index == -1)
			return 0;

		int sum = 0;
		for (int i = 0; i < getConceptCount(); i++) {
			if (adjacencyMatrix[i][index] != null)
				sum++;
		}
		return sum;
	}

	public int getOutgoingLinkCount(Concept c) {
		int index = indexOf(c);
		if (index == -1)
			return 0;

		int sum = 0;
		for (int i = 0; i < getConceptCount(); i++) {
			if (adjacencyMatrix[index][i] != null)
				sum++;
		}
		return sum;
	}

}
