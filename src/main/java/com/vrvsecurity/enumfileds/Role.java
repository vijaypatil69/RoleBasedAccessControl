package com.vrvsecurity.enumfileds;

public enum Role {
	ADMIN(1), USER(2);

	private final int value;

	Role(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static int getValueByName(String name) {
		try {
			for (Role role : Role.values()) {
				if (role.name().equalsIgnoreCase(name)) {
					return role.getValue();
				}
			}
			throw new IllegalArgumentException("Invalid Role : " + name);
		} catch (IllegalArgumentException ex) {
			throw ex;
		}
	}

	public static String getByValue(int value) {
		for (Role role : Role.values()) {
			if (role.getValue() == value) {
				return role.name(); 
			}
		}
		throw new IllegalArgumentException("Unexpected value: " + value);
	}
}
