public class Pair<T> {
	public T a;
	public T b;

	public Pair(T a, T b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Pair<?> other = (Pair<?>) obj;
		return a.equals(other.a) && b.equals(other.b);
	}

	@Override
	public int hashCode() {
		int result = a != null ? a.hashCode() : 0;
		result = 31 * result + (b != null ? b.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "(" + a.toString() + ", " + b.toString() + ")";
	}
}
