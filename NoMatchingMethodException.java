public class NoMatchingMethodException extends RuntimeException {
	private static final long serialVersionUID = -6818566028119818868L;
	private Signature signature;

	public NoMatchingMethodException(Signature sig) {
		super(composeMessage(sig));
		signature = sig;
	}

	private static String composeMessage(Signature sig) {
		return String.format("No matching method for type signature %s", sig);
	}
	public Signature getSignature() {
		return signature;
	}
}