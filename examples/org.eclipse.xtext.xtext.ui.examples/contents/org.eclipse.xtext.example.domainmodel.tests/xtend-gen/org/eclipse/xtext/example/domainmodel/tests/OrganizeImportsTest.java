package org.eclipse.xtext.example.domainmodel.tests;

import com.google.inject.Inject;
import java.util.List;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.example.domainmodel.DomainmodelInjectorProvider;
import org.eclipse.xtext.example.domainmodel.domainmodel.DomainModel;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.util.ParseHelper;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.ReplaceRegion;
import org.eclipse.xtext.xbase.imports.ImportOrganizer;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(value = XtextRunner.class)
@InjectWith(value = DomainmodelInjectorProvider.class)
@SuppressWarnings("all")
public class OrganizeImportsTest {
  @Inject
  private ParseHelper<DomainModel> _parseHelper;
  
  @Inject
  private ImportOrganizer importOrganizer;
  
  protected void assertIsOrganizedTo(final CharSequence model, final CharSequence expected) {
    try {
      String _string = model.toString();
      final DomainModel domainModel = this._parseHelper.parse(_string);
      Resource _eResource = domainModel.eResource();
      final List<ReplaceRegion> changes = this.importOrganizer.getOrganizedImportChanges(((XtextResource) _eResource));
      StringBuilder _stringBuilder = new StringBuilder(model);
      final StringBuilder builder = _stringBuilder;
      final Function1<ReplaceRegion,Integer> _function = new Function1<ReplaceRegion,Integer>() {
          public Integer apply(final ReplaceRegion it) {
            int _offset = it.getOffset();
            return Integer.valueOf(_offset);
          }
        };
      List<ReplaceRegion> _sortBy = IterableExtensions.<ReplaceRegion, Integer>sortBy(changes, _function);
      List<ReplaceRegion> _reverse = ListExtensions.<ReplaceRegion>reverse(_sortBy);
      for (final ReplaceRegion it : _reverse) {
        int _offset = it.getOffset();
        int _offset_1 = it.getOffset();
        int _length = it.getLength();
        int _plus = (_offset_1 + _length);
        String _text = it.getText();
        builder.replace(_offset, _plus, _text);
      }
      String _string_1 = expected.toString();
      String _string_2 = builder.toString();
      Assert.assertEquals(_string_1, _string_2);
    } catch (Exception _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void testSimple() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package foo {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("entity Foo extends java.io.Serializable {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import java.io.Serializable");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("package foo {");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("entity Foo extends Serializable {}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertIsOrganizedTo(_builder, _builder_1);
  }
  
  @Test
  public void testDefaultPackage() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("entity Foo extends java.io.Serializable {}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import java.io.Serializable");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("entity Foo extends Serializable {}");
    _builder_1.newLine();
    this.assertIsOrganizedTo(_builder, _builder_1);
  }
  
  @Test
  public void testDefaultPackageLeadingWhitespace() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    _builder.append("   \t");
    _builder.newLine();
    _builder.append("entity Foo extends java.io.Serializable {}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import java.io.Serializable");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("entity Foo extends Serializable {}");
    _builder_1.newLine();
    this.assertIsOrganizedTo(_builder, _builder_1);
  }
  
  @Test
  public void testDefaultPackageWithJavaDoc() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* some doc");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("entity Foo extends java.io.Serializable {}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import java.io.Serializable");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("/**");
    _builder_1.newLine();
    _builder_1.append(" ");
    _builder_1.append("* some doc");
    _builder_1.newLine();
    _builder_1.append(" ");
    _builder_1.append("*/");
    _builder_1.newLine();
    _builder_1.append("entity Foo extends Serializable {}");
    _builder_1.newLine();
    this.assertIsOrganizedTo(_builder, _builder_1);
  }
  
  @Test
  public void testGetOrganizedImportSection_01() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import java.lang.String");
    _builder.newLine();
    _builder.append("import java.util.List");
    _builder.newLine();
    _builder.newLine();
    _builder.append("entity Foo {");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("op test(List<String> s) : void {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import java.util.List");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("entity Foo {");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("op test(List<String> s) : void {}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertIsOrganizedTo(_builder, _builder_1);
  }
  
  @Test
  public void testGetOrganizedImportSection_02() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import java.lang.String");
    _builder.newLine();
    _builder.append("import java.util.List");
    _builder.newLine();
    _builder.append("import java.util.List");
    _builder.newLine();
    _builder.append("import java.util.List");
    _builder.newLine();
    _builder.append("import java.lang.Integer");
    _builder.newLine();
    _builder.newLine();
    _builder.append("entity Foo {");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("op test(List<String> s) : void {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import java.util.List");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("entity Foo {");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("op test(List<String> s) : void {}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertIsOrganizedTo(_builder, _builder_1);
  }
  
  @Test
  public void testInnerClasses_01() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("entity Foo {");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("op test(org.eclipse.emf.ecore.resource.Resource$Factory a, org.eclipse.emf.ecore.resource.Resource$Factory$Registry b) : void {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import org.eclipse.emf.ecore.resource.Resource");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("entity Foo {");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("op test(Resource$Factory a, Resource$Factory$Registry b) : void {}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertIsOrganizedTo(_builder, _builder_1);
  }
  
  @Test
  public void testInnerClasses_02() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import org.eclipse.emf.ecore.resource.Resource");
    _builder.newLine();
    _builder.append("import org.eclipse.emf.ecore.EPackage");
    _builder.newLine();
    _builder.newLine();
    _builder.append("entity Foo {");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("op test() : void {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("val x = Resource$Factory$Registry::INSTANCE");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("val y = EPackage$Registry::INSTANCE");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import org.eclipse.emf.ecore.EPackage");
    _builder_1.newLine();
    _builder_1.append("import org.eclipse.emf.ecore.resource.Resource");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("entity Foo {");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("op test() : void {");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("val x = Resource$Factory$Registry::INSTANCE");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("val y = EPackage$Registry::INSTANCE");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertIsOrganizedTo(_builder, _builder_1);
  }
  
  @Test
  public void testInnerClasses_03() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("entity Foo {");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("op test() : boolean {");
    _builder.newLine();
    _builder.append("\t ");
    _builder.append("typeof(org.eclipse.emf.ecore.resource.Resource$Factory) == typeof(org.eclipse.emf.ecore.resource.Resource$Factory$Registry)");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import org.eclipse.emf.ecore.resource.Resource");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("entity Foo {");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("op test() : boolean {");
    _builder_1.newLine();
    _builder_1.append("\t ");
    _builder_1.append("typeof(Resource$Factory) == typeof(Resource$Factory$Registry)");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertIsOrganizedTo(_builder, _builder_1);
  }
  
  @Test
  public void testInnerClasses_04() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import org.eclipse.emf.ecore.resource.Resource");
    _builder.newLine();
    _builder.append("import org.eclipse.emf.ecore.EPackage");
    _builder.newLine();
    _builder.newLine();
    _builder.append("entity Foo {");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("op test() : void {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("typeof(Resource$Factory$Registry) == typeof(EPackage$Registry)");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import org.eclipse.emf.ecore.EPackage");
    _builder_1.newLine();
    _builder_1.append("import org.eclipse.emf.ecore.resource.Resource");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("entity Foo {");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("op test() : void {");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("typeof(Resource$Factory$Registry) == typeof(EPackage$Registry)");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertIsOrganizedTo(_builder, _builder_1);
  }
  
  @Test
  public void testInnerClasses_05() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import org.eclipse.emf.ecore.resource.Resource$Factory$Registry");
    _builder.newLine();
    _builder.newLine();
    _builder.append("entity Foo {");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("op test() : void {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("typeof(Registry)");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import org.eclipse.emf.ecore.resource.Resource$Factory$Registry");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("entity Foo {");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("op test() : void {");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("typeof(Registry)");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertIsOrganizedTo(_builder, _builder_1);
  }
  
  @Test
  public void testNameClashSameFileWins_1() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package foo {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("entity Foo {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("x : java.lang.String");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("entity String {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("package foo {");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("entity Foo {");
    _builder_1.newLine();
    _builder_1.append("\t\t");
    _builder_1.append("x : java.lang.String");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("entity String {}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertIsOrganizedTo(_builder, _builder_1);
  }
  
  @Test
  public void testNameClashSameFileWins() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package foo {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("entity Foo extends java.io.Serializable{}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("entity Serializable {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("package foo {");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("entity Foo extends java.io.Serializable{}");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("entity Serializable {}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertIsOrganizedTo(_builder, _builder_1);
  }
  
  @Test
  public void testNameClashOrder_01() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("entity Foo {");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("op test(java.util.List<String> s) : java.awt.List {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("return null");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import java.awt.List");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("entity Foo {");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("op test(java.util.List<String> s) : List {");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("return null");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertIsOrganizedTo(_builder, _builder_1);
  }
  
  @Test
  public void testNameClashOrder_02() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("entity Foo {");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("op test(java.awt.List<String> s) : java.util.List  {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("return null");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import java.util.List");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("entity Foo {");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("op test(java.awt.List<String> s) : List  {");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("return null");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertIsOrganizedTo(_builder, _builder_1);
  }
  
  @Test
  public void testNameClashMoreCommon() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("entity Foo {");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("op test(java.awt.List s) : java.util.List<String> {");
    _builder.newLine();
    _builder.append("  \t");
    _builder.append("new java.awt.List()");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("return null");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import java.awt.List");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("entity Foo {");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("op test(List s) : java.util.List<String> {");
    _builder_1.newLine();
    _builder_1.append("  \t");
    _builder_1.append("new List()");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("return null");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertIsOrganizedTo(_builder, _builder_1);
  }
  
  @Ignore
  @Test
  public void testStaticImport_01() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import static java.util.Collections.*");
    _builder.newLine();
    _builder.append("entity Foo {");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("op test(java.util.List<String> s) : void {");
    _builder.newLine();
    _builder.append("  \t");
    _builder.append("shuffle(s)");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import java.util.List");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("import static java.util.Collections.*");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("entity Foo {");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("op test(List<String> s) : void {");
    _builder_1.newLine();
    _builder_1.append("  \t");
    _builder_1.append("singleton(s)");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertIsOrganizedTo(_builder, _builder_1);
  }
  
  @Ignore
  @Test
  public void testStaticImport_02() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import static extension java.util.Collections.*");
    _builder.newLine();
    _builder.append("class Foo {");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("def void test(java.util.List<String> s) {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("s.sort");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import java.util.List");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("import static extension java.util.Collections.*");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("entity Foo {");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("op test(List<String> s) : void {");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("s.sort");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertIsOrganizedTo(_builder, _builder_1);
  }
  
  @Test
  public void testImplicitExtensions() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("entity Foo {");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("op test(java.util.List<String> s) : void {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("s.head");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import java.util.List");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("entity Foo {");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("op test(List<String> s) : void {");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("s.head");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertIsOrganizedTo(_builder, _builder_1);
  }
  
  @Ignore
  @Test
  public void testStaticExtensions() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import static extension java.util.Collections.*");
    _builder.newLine();
    _builder.append("entity Foo {");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("op test() : void {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("typeof(java.util.Collections).singleton");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import java.util.Collections");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("import static extension java.util.Collections.*");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("entity Foo {");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("op test() : void {");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("typeof(Collections).singleton");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertIsOrganizedTo(_builder, _builder_1);
  }
  
  @Test
  public void testInnerClassImport_01() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import java.util.Map$Entry");
    _builder.newLine();
    _builder.newLine();
    _builder.append("package foo.bar {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("entity Foo {");
    _builder.newLine();
    _builder.append("\t  ");
    _builder.append("op test() : Entry {");
    _builder.newLine();
    _builder.append("\t    ");
    _builder.append("return null");
    _builder.newLine();
    _builder.append("\t  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import java.util.Map$Entry");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("package foo.bar {");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("entity Foo {");
    _builder_1.newLine();
    _builder_1.append("\t  ");
    _builder_1.append("op test() : Entry {");
    _builder_1.newLine();
    _builder_1.append("\t    ");
    _builder_1.append("return null");
    _builder_1.newLine();
    _builder_1.append("\t  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertIsOrganizedTo(_builder, _builder_1);
  }
  
  @Test
  public void testInnerClassImport_02() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import java.util.Map");
    _builder.newLine();
    _builder.newLine();
    _builder.append("package foo.bar {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("entity Foo {");
    _builder.newLine();
    _builder.append("\t  ");
    _builder.append("op test() : Map$Entry {");
    _builder.newLine();
    _builder.append("\t    ");
    _builder.append("return null");
    _builder.newLine();
    _builder.append("\t  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import java.util.Map");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("package foo.bar {");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("entity Foo {");
    _builder_1.newLine();
    _builder_1.append("\t  ");
    _builder_1.append("op test() : Map$Entry {");
    _builder_1.newLine();
    _builder_1.append("\t    ");
    _builder_1.append("return null");
    _builder_1.newLine();
    _builder_1.append("\t  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertIsOrganizedTo(_builder, _builder_1);
  }
  
  @Test
  public void testInnerClassImport_03() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import java.util.Map$Entry");
    _builder.newLine();
    _builder.newLine();
    _builder.append("package foo.bar {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("entity Foo {");
    _builder.newLine();
    _builder.append("\t  ");
    _builder.append("op test() : Map$Entry {");
    _builder.newLine();
    _builder.append("\t    ");
    _builder.append("return null");
    _builder.newLine();
    _builder.append("\t  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import java.util.Map");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("package foo.bar {");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("entity Foo {");
    _builder_1.newLine();
    _builder_1.append("\t  ");
    _builder_1.append("op test() : Map$Entry {");
    _builder_1.newLine();
    _builder_1.append("\t    ");
    _builder_1.append("return null");
    _builder_1.newLine();
    _builder_1.append("\t  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertIsOrganizedTo(_builder, _builder_1);
  }
  
  @Test
  public void testInnerClassImport_04() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import org.eclipse.emf.ecore.resource.Resource");
    _builder.newLine();
    _builder.newLine();
    _builder.append("package foo.bar {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("entity Foo {");
    _builder.newLine();
    _builder.append("\t  ");
    _builder.append("op test() : Resource$Factory$Descriptor {");
    _builder.newLine();
    _builder.append("\t    ");
    _builder.append("return null");
    _builder.newLine();
    _builder.append("\t  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import org.eclipse.emf.ecore.resource.Resource");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("package foo.bar {");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("entity Foo {");
    _builder_1.newLine();
    _builder_1.append("\t  ");
    _builder_1.append("op test() : Resource$Factory$Descriptor {");
    _builder_1.newLine();
    _builder_1.append("\t    ");
    _builder_1.append("return null");
    _builder_1.newLine();
    _builder_1.append("\t  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertIsOrganizedTo(_builder, _builder_1);
  }
  
  @Test
  public void testInnerClassImport_05() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import org.eclipse.emf.ecore.resource.Resource$Factory$Descriptor");
    _builder.newLine();
    _builder.newLine();
    _builder.append("package foo.bar {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("entity Foo {");
    _builder.newLine();
    _builder.append("\t  ");
    _builder.append("op test() : Resource$Factory$Descriptor {");
    _builder.newLine();
    _builder.append("\t    ");
    _builder.append("return null");
    _builder.newLine();
    _builder.append("\t  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import org.eclipse.emf.ecore.resource.Resource");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("package foo.bar {");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("entity Foo {");
    _builder_1.newLine();
    _builder_1.append("\t  ");
    _builder_1.append("op test() : Resource$Factory$Descriptor {");
    _builder_1.newLine();
    _builder_1.append("\t    ");
    _builder_1.append("return null");
    _builder_1.newLine();
    _builder_1.append("\t  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertIsOrganizedTo(_builder, _builder_1);
  }
  
  @Test
  public void testInnerClassImport_06() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import org.eclipse.emf.ecore.resource.Resource$Factory$Descriptor");
    _builder.newLine();
    _builder.newLine();
    _builder.append("package foo.bar {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("entity Foo {");
    _builder.newLine();
    _builder.append("\t  ");
    _builder.append("op test() : Descriptor {");
    _builder.newLine();
    _builder.append("\t    ");
    _builder.append("return null");
    _builder.newLine();
    _builder.append("\t  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import org.eclipse.emf.ecore.resource.Resource$Factory$Descriptor");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("package foo.bar {");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("entity Foo {");
    _builder_1.newLine();
    _builder_1.append("\t  ");
    _builder_1.append("op test() : Descriptor {");
    _builder_1.newLine();
    _builder_1.append("\t    ");
    _builder_1.append("return null");
    _builder_1.newLine();
    _builder_1.append("\t  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertIsOrganizedTo(_builder, _builder_1);
  }
  
  @Test
  public void testFunctionTypes_afterResolve() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import java.util.Map$Entry");
    _builder.newLine();
    _builder.newLine();
    _builder.append("package foo.bar {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("entity Foo {");
    _builder.newLine();
    _builder.append("\t  ");
    _builder.append("op test() : (Entry)=>void {");
    _builder.newLine();
    _builder.append("\t    ");
    _builder.append("return null");
    _builder.newLine();
    _builder.append("\t  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import java.util.Map$Entry");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("package foo.bar {");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("entity Foo {");
    _builder_1.newLine();
    _builder_1.append("\t  ");
    _builder_1.append("op test() : (Entry)=>void {");
    _builder_1.newLine();
    _builder_1.append("\t    ");
    _builder_1.append("return null");
    _builder_1.newLine();
    _builder_1.append("\t  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertIsOrganizedTo(_builder, _builder_1);
  }
  
  @Ignore
  @Test
  public void testImport_PairOf() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import static org.eclipse.xtext.xbase.lib.Pair.*");
    _builder.newLine();
    _builder.newLine();
    _builder.append("package foo.bar {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("entity Foo {");
    _builder.newLine();
    _builder.append("\t  ");
    _builder.append("op test() : Object {");
    _builder.newLine();
    _builder.append("\t    ");
    _builder.append("return of(\'\', \'\')");
    _builder.newLine();
    _builder.append("\t  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import static org.eclipse.xtext.xbase.lib.Pair.*");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("package foo.bar {");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("entity Foo {");
    _builder_1.newLine();
    _builder_1.append("\t  ");
    _builder_1.append("op test() : Object {");
    _builder_1.newLine();
    _builder_1.append("\t    ");
    _builder_1.append("return of(\'\', \'\')");
    _builder_1.newLine();
    _builder_1.append("\t  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertIsOrganizedTo(_builder, _builder_1);
  }
  
  @Test
  public void testArrayType() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("import java.io.Serializable");
    _builder.newLine();
    _builder.newLine();
    _builder.append("package foo.bar {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("entity Foo {");
    _builder.newLine();
    _builder.append("\t  ");
    _builder.append("op test() : Serializable[][] {");
    _builder.newLine();
    _builder.append("\t    ");
    _builder.append("return null");
    _builder.newLine();
    _builder.append("\t  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import java.io.Serializable");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("package foo.bar {");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("entity Foo {");
    _builder_1.newLine();
    _builder_1.append("\t  ");
    _builder_1.append("op test() : Serializable[][] {");
    _builder_1.newLine();
    _builder_1.append("\t    ");
    _builder_1.append("return null");
    _builder_1.newLine();
    _builder_1.append("\t  ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertIsOrganizedTo(_builder, _builder_1);
  }
  
  @Test
  public void testClassWithSameName() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package foo.bar {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("entity Serializable {");
    _builder.newLine();
    _builder.append("\t  ");
    _builder.append("clazz : Class<? extends java.io.Serializable>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("package foo.bar {");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("entity Serializable {");
    _builder_1.newLine();
    _builder_1.append("\t  ");
    _builder_1.append("clazz : Class<? extends java.io.Serializable>");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.assertIsOrganizedTo(_builder, _builder_1);
  }
  
  @Test
  public void testJavaDoc() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("/**");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* {@link java.util.List}");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("entity Foo {}");
    _builder.newLine();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("import java.util.List");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("/**");
    _builder_1.newLine();
    _builder_1.append(" ");
    _builder_1.append("* {@link List}");
    _builder_1.newLine();
    _builder_1.append(" ");
    _builder_1.append("*/");
    _builder_1.newLine();
    _builder_1.append("entity Foo {}");
    _builder_1.newLine();
    this.assertIsOrganizedTo(_builder, _builder_1);
  }
}