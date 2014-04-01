package com.cppoon.tencent.magiccard.test;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Categories.class)
@ExcludeCategory(ManualTests.class)
@SuiteClasses(AllTestsSuite.class)
public class UnitTestSuite {


}
