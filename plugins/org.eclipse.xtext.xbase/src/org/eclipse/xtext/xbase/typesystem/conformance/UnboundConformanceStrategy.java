/*******************************************************************************
 * Copyright (c) 2011 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.xbase.typesystem.conformance;

import java.util.List;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.xtext.xbase.typesystem.references.CompoundTypeReference;
import org.eclipse.xtext.xbase.typesystem.references.LightweightBoundTypeArgument;
import org.eclipse.xtext.xbase.typesystem.references.LightweightMergedBoundTypeArgument;
import org.eclipse.xtext.xbase.typesystem.references.LightweightTypeReference;
import org.eclipse.xtext.xbase.typesystem.references.UnboundTypeReference;
import org.eclipse.xtext.xbase.typesystem.util.BoundTypeArgumentMerger;
import org.eclipse.xtext.xbase.typesystem.util.BoundTypeArgumentSource;

import com.google.common.collect.Lists;

/**
 * @author Sebastian Zarnekow - Initial contribution and API
 */
@NonNullByDefault
class UnboundConformanceStrategy extends TypeConformanceStrategy<UnboundTypeReference> {
	protected UnboundConformanceStrategy(TypeConformanceComputer conformanceComputer) {
		super(conformanceComputer);
	}

	@Override
	protected TypeConformanceResult doVisitTypeReference(UnboundTypeReference left, LightweightTypeReference right, TypeConformanceComputationArgument.Internal<UnboundTypeReference> param) {
		TypeConformanceResult result = tryResolveAndCheckConformance(left, right, param);
		if (result != null)
			return result;
		return TypeConformanceResult.FAILED;
	}

	@Nullable
	protected TypeConformanceResult tryResolveAndCheckConformance(UnboundTypeReference left, LightweightTypeReference right,
			TypeConformanceComputationArgument.Internal<UnboundTypeReference> param) {
		List<LightweightBoundTypeArgument> hints = left.getAllHints();
		List<LightweightBoundTypeArgument> hintsToProcess = Lists.newArrayListWithCapacity(hints.size());
		List<LightweightBoundTypeArgument> inferredHintsToProcess = Lists.newArrayListWithCapacity(hints.size());
		for(LightweightBoundTypeArgument hint: hints) {
			if (hint.getDeclaredVariance() != null) {
				hintsToProcess.add(hint);
				if (hint.getSource() == BoundTypeArgumentSource.INFERRED) {
					inferredHintsToProcess.add(hint);
				}
			}
		}
		BoundTypeArgumentMerger merger = left.getOwner().getServices().getBoundTypeArgumentMerger();
		LightweightMergedBoundTypeArgument mergeResult = merger.merge(inferredHintsToProcess.isEmpty() ? hintsToProcess : inferredHintsToProcess, left.getOwner());
		if (mergeResult != null) {
			TypeConformanceResult result = conformanceComputer.isConformant(mergeResult.getTypeReference(), right, param);
			return result;
		}
		return null;
	}
	
	@Override
	protected TypeConformanceResult doVisitMultiTypeReference(UnboundTypeReference left, CompoundTypeReference right, TypeConformanceComputationArgument.Internal<UnboundTypeReference> param) {
		return doVisitTypeReference(left, right, param);
	}
	
	@Override
	protected TypeConformanceResult doVisitUnboundTypeReference(UnboundTypeReference left, UnboundTypeReference right,
			TypeConformanceComputationArgument.Internal<UnboundTypeReference> param) {
		if (left.getTypeParameter() == right.getTypeParameter())
			return TypeConformanceResult.SUCCESS;
		if (left.getAllHints().equals(right.getAllHints()))
			return TypeConformanceResult.SUCCESS;
		return TypeConformanceResult.FAILED;
	}
}