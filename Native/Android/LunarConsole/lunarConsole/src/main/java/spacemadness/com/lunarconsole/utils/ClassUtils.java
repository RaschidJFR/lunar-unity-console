//
//  ClassUtils.java
//
//  Lunar Unity Mobile Console
//  https://github.com/SpaceMadness/lunar-unity-console
//
//  Copyright 2019 Alex Lementuev, SpaceMadness.
//
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.
//

package spacemadness.com.lunarconsole.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static spacemadness.com.lunarconsole.utils.ObjectUtils.checkNotNull;

public final class ClassUtils {
	private static final Class<?>[] EMPTY_PARAMS_TYPES = new Class[0];
	private static final Object[] EMPTY_ARGS = new Object[0];

	public static <T> T newInstance(Class<T> cls) {
		try {
			Constructor<T> constructor = checkNotNull(cls, "cls").getDeclaredConstructor(EMPTY_PARAMS_TYPES);
			constructor.setAccessible(true);
			return constructor.newInstance(EMPTY_ARGS);

		} catch (Exception e) {
			throw new RuntimeException(e); // TODO: custom class
		}
	}

	public static Field[] listFields(Class<?> cls) {
		return cls.getDeclaredFields();
	}

	public static List<Field> listFields(Class<?> cls, FieldFilter filter) {
		final Field[] fields = cls.getDeclaredFields();
		final List<Field> result = new ArrayList<>(fields.length);

		for (Field field : fields) {
			if (filter.accept(field)) {
				result.add(field);
			}
		}

		return result;
	}

	public static Field setField(Field field, Object target, Object value) {
		field.setAccessible(true);
		try {
			field.set(target, value);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Unable to set field value: " + field); // TODO: custom class
		}
		return field;
	}

	public interface FieldFilter {
		boolean accept(Field field);
	}
}
