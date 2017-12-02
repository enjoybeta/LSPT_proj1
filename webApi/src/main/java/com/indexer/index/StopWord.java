package com.indexer.index;


public class StopWord implements Comparable<StopWord> {
	String word = new String();
	int n_gram_size;
	long occurences;
	
	public StopWord(String word, int n_gram_size, int occurences) {
		super();
		this.word = word;
		this.n_gram_size = n_gram_size;
		this.occurences = occurences;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		//if they are the same word except for occurences we want to say they are
		//equal, if occurences is not equal update the others number of occurences.
		StopWord other = (StopWord) obj;
		if (n_gram_size != other.n_gram_size)
			return false;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}
	
	public int compareTo(StopWord arg0) {
		if(occurences > arg0.occurences) {
			return 1; //more of this stopword than other so I am 'greater'
		}
		if(occurences == arg0.occurences) {
			return 0; //same # of occurences -> so i am equal
		}
		return -1; //fewer occurences of me so i am 'less' than other stopword
	}
	
}