/*
 * Copyright (c) 2020 the original author or authors.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package science.aist.gtf.graph.validator.impl;

import science.aist.gtf.graph.GraphAnalyzer;
import science.aist.gtf.graph.GraphStateAccessor;
import science.aist.gtf.graph.validator.GraphStateValidator;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Predicate;

/**
 * <p>Tests if a given graphState confirms to a list of graph analyzer and their rules</p>
 *
 * @param <V> the type of the decorated vertex value
 * @param <E> the type of the decorated edge value
 * @author Andreas Pointner
 * @since 1.0
 */
public class RuleBasedGraphStateValidatorImpl<V, E> implements GraphStateValidator<V, E> {

    private final Collection<Predicate<GraphStateAccessor<V, E>>> graphRuleAnalyzers = new HashSet<>();

    public <R> void addAnalyzerRule(GraphAnalyzer<V, E, R> graphAnalyzer, Predicate<R> rule) {
        graphRuleAnalyzers.add(graphState -> rule.test(graphAnalyzer.analyzeGraphState(graphState)));
    }

    @Override
    public boolean isValidGraphState(GraphStateAccessor<V, E> graphState) {
        return graphRuleAnalyzers.stream().allMatch(p -> p.test(graphState));
    }

}
