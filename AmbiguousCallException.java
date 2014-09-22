

import java.util.Collection;

public class AmbiguousCallException extends RuntimeException {
	private static final long serialVersionUID = -57074539022863248L;
	public Collection<Signature> signatures;

	public Collection<Signature> getSignatures() {
		return signatures;
	}

	public AmbiguousCallException(Collection<Signature> signatures) {
		super(composeMessage(signatures));
		this.signatures = signatures;
	}

	private static String composeMessage(Collection<Signature> signatures) {
		String msg = "Ambiguous call. Matching methods: ";

		for (Signature sig : signatures) {
			msg += "\n" + sig;
		}

		return msg;
	}
}