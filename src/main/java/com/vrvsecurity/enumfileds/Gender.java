package com.vrvsecurity.enumfileds;

public enum Gender {
	MALE(1), FEMALE(2), OTHER(3);

	private final int value;

	Gender(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static String getByValue(int value) {
		try {
			for (Gender gender : Gender.values()) {
				if (gender.getValue() == value) {
					return gender.name();
				}

			}
			throw new IllegalArgumentException("Unexpected value: " + value);
		} catch (IllegalArgumentException ex) {
			throw ex;
		}
	}

	public static int getValueByName(String name) {

		try {
			for (Gender gender : Gender.values()) {
				if (gender.name().equalsIgnoreCase(name)) {
					return gender.getValue();
				}
			}
			throw new IllegalArgumentException("Invalid Gender : " + name);
		} catch (IllegalArgumentException ex) {
			throw ex;
		}
	}
}
