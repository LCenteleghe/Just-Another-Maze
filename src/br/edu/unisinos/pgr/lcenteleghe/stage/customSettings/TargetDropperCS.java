package br.edu.unisinos.pgr.lcenteleghe.stage.customSettings;

public class TargetDropperCS{
	private float x;
	private float y;
	private float dropperWidth;
	private float maxHeight;
	private long dropRateInSeconds;
	private long fallSpeedInMilliseconds;
	private boolean diferentSpeedPerTarget;
	
	

	public TargetDropperCS(float x, float y, float dropperWidth,
			float maxHeight, long dropRateInSeconds,
			long fallSpeedInMilliseconds, boolean diferentSpeedPerTarget) {
		super();
		this.x = x;
		this.y = y;
		this.dropperWidth = dropperWidth;
		this.maxHeight = maxHeight;
		this.dropRateInSeconds = dropRateInSeconds;
		this.fallSpeedInMilliseconds = fallSpeedInMilliseconds;
		this.diferentSpeedPerTarget = diferentSpeedPerTarget;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getDropperWidth() {
		return dropperWidth;
	}
	public void setDropperWidth(float dropperWidth) {
		this.dropperWidth = dropperWidth;
	}
	public long getDropRateInSeconds() {
		return dropRateInSeconds;
	}
	public void setDropRateInSeconds(long dropRateInSeconds) {
		this.dropRateInSeconds = dropRateInSeconds;
	}
	public boolean isDiferentSpeedPerTarget() {
		return diferentSpeedPerTarget;
	}
	public void setDiferentSpeedPerTarget(boolean diferentSpeedPerTarget) {
		this.diferentSpeedPerTarget = diferentSpeedPerTarget;
	}
	public long getFallSpeedInMilliseconds() {
		return fallSpeedInMilliseconds;
	}
	public void setFallSpeedInMilliseconds(long fallSpeedInMilliseconds) {
		this.fallSpeedInMilliseconds = fallSpeedInMilliseconds;
	}
	public float getMaxHeight() {
		return maxHeight;
	}
	public void setMaxHeight(float maxHeight) {
		this.maxHeight = maxHeight;
	}
}