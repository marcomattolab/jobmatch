package it.aranciaict.jobmatch.domain.constants;

/**
 * Classe contentente le costanti per la validazine dei campi
 * 
 * @author Giuseppe
 *
 */
public final class ValidationConstants {

	public static final int SIZE_1 = 1;
	public static final int SIZE_2 = 2;
	public static final int SIZE_5 = 5;
	public static final int SIZE_6 = 6;
	public static final int SIZE_10 = 10;
	public static final int SIZE_20 = 20;
	public static final int SIZE_30 = 30;
	public static final int SIZE_50 = 50;
	public static final int SIZE_60 = 60;
	public static final int SIZE_80 = 80;
	public static final int SIZE_100 = 100;
	public static final int SIZE_120 = 120;
	public static final int SIZE_254 = 254;
	public static final int SIZE_255 = 255;
	public static final int SIZE_256 = 256;
	public static final int SIZE_512 = 512;
	public static final int SIZE_1000 = 1000;

	public static final String PHONE_REG_EXP = "^[0-9+ ]{8,15}$";
	public static final String URL_SITE_REG_EXP = "^www.[^@\\s]+\\.[^@\\s]+$";
	public static final String VAT_CODE_REG_EXP = "^[0-9]{11}$";

	/**
	 * Instantiates a new validation constants.
	 */
	private ValidationConstants() {
	}

}
