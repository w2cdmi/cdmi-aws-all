package pw.cdmi.collection;

public class Option<V, T> {
	private V value;
	private T text;

	public Option(V value, T text) {
		this.text = text;
		this.value = value;
	}

	public T getText() {
		return text;
	}

	public void setText(T text) {
		this.text = text;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

}
