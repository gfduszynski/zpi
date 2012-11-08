/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.businessTier.businessObjects;

import java.util.List;
import org.chessclan.dataTier.models.Category;

/**
 *
 * @author Xcays
 */
public interface CategoryBO{
    public Category saveCategory(Category c);
    public Iterable<Category> saveCategories(Iterable<Category> c);
    public Category findCategoryById(int id);
    public Iterable<Category> findCategoriesById(Iterable<Integer> ids);
    public List<Category> findAll();
    public void deleteCategory(int id);
    public void deleteCategory(Category c);
    public void deleteCategories(Iterable<Category> cs);
}
