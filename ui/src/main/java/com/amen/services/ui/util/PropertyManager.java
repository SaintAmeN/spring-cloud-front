package com.amen.services.ui.util;

import lombok.NoArgsConstructor;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.beans.BeanUtils.copyProperties;

@NoArgsConstructor
public abstract class PropertyManager {

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for(PropertyDescriptor descriptor : propertyDescriptors) {
            Object srcValue = beanWrapper.getPropertyValue(descriptor.getName());
            if (srcValue == null) emptyNames.add(descriptor.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static void copyNonNullProperties(Object source, Object target) {
        copyProperties(source, target, getNullPropertyNames(source));
    }
}
