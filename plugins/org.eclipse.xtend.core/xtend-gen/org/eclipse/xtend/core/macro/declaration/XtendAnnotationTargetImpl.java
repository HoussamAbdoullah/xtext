/**
 * Copyright (c) 2013 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.xtend.core.macro.declaration;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend.core.macro.declaration.CompilationUnitImpl;
import org.eclipse.xtend.core.macro.declaration.XtendNamedElementImpl;
import org.eclipse.xtend.core.xtend.XtendAnnotationTarget;
import org.eclipse.xtend.lib.macro.declaration.AnnotationReference;
import org.eclipse.xtend.lib.macro.declaration.AnnotationTarget;
import org.eclipse.xtend.lib.macro.declaration.AnnotationTypeDeclaration;
import org.eclipse.xtend.lib.macro.declaration.Type;
import org.eclipse.xtext.xbase.annotations.xAnnotations.XAnnotation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public abstract class XtendAnnotationTargetImpl<T extends XtendAnnotationTarget> extends XtendNamedElementImpl<T> implements AnnotationTarget {
  public Iterable<? extends AnnotationReference> getAnnotations() {
    T _delegate = this.getDelegate();
    EList<XAnnotation> _annotations = _delegate.getAnnotations();
    final Function1<XAnnotation,AnnotationReference> _function = new Function1<XAnnotation,AnnotationReference>() {
      public AnnotationReference apply(final XAnnotation it) {
        CompilationUnitImpl _compilationUnit = XtendAnnotationTargetImpl.this.getCompilationUnit();
        AnnotationReference _annotationReference = _compilationUnit.toAnnotationReference(it);
        return _annotationReference;
      }
    };
    List<AnnotationReference> _map = ListExtensions.<XAnnotation, AnnotationReference>map(_annotations, _function);
    ImmutableList<AnnotationReference> _copyOf = ImmutableList.<AnnotationReference>copyOf(_map);
    return _copyOf;
  }
  
  public AnnotationReference findAnnotation(final Type annotationType) {
    Iterable<? extends AnnotationReference> _annotations = this.getAnnotations();
    final Function1<AnnotationReference,Boolean> _function = new Function1<AnnotationReference,Boolean>() {
      public Boolean apply(final AnnotationReference it) {
        AnnotationTypeDeclaration _annotationTypeDeclaration = it.getAnnotationTypeDeclaration();
        boolean _equals = Objects.equal(_annotationTypeDeclaration, annotationType);
        return Boolean.valueOf(_equals);
      }
    };
    AnnotationReference _findFirst = IterableExtensions.findFirst(_annotations, _function);
    return _findFirst;
  }
}
