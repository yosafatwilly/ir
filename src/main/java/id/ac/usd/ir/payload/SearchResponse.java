package id.ac.usd.ir.payload;

import id.ac.usd.ir.entity.Document;

/**
 * @author Yosafat Willy Christian
 */
public class SearchResponse implements Comparable
{
	private Document document = null;

	private double score = -1;

	public SearchResponse(Document document, double score) {
		this.document = document;
		this.score = score;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	@Override
	public int compareTo(Object o) {
		if(this.score == ((SearchResponse)o).score)
			return this.document.compareTo(((SearchResponse)o).document);
		return -1*Double.compare(this.score,((SearchResponse)o).score);
	}
}
