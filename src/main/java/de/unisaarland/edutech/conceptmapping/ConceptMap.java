package de.unisaarland.edutech.conceptmapping;

import java.util.ArrayList;
import java.util.List;

public class ConceptMap {

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
		return isLinkedDirected(indexOf(a), indexOf(b));
	}

	public boolean isLinkedUndirected(Concept a, Concept b) {
		return isLinkedUndirected(indexOf(a), indexOf(b));
	}

	public boolean isLinkedDirected(int i, int j) {
		return adjacencyMatrix[i][j] != null;
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

		return adjacencyMatrix[i][j];
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
		return builder.toString();
	}
}
