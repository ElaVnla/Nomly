package com.nomlybackend.nomlybackend.model;

import com.nomlybackend.nomlybackend.model.eateries.Nearby.ExcludedType;
import com.nomlybackend.nomlybackend.model.eateries.Nearby.IncludedType;

public class DietaryRestrictions {
    private final IncludedType[] inclusions;
    private final ExcludedType[] exclusions;

    public DietaryRestrictions(IncludedType[] inclusions, ExcludedType[] exclusions) {
        this.inclusions = inclusions;
        this.exclusions = exclusions;
    }

    public IncludedType[] getInclusions() {
        return inclusions;
    }

    public ExcludedType[] getExclusions() {
        return exclusions;
    }
}