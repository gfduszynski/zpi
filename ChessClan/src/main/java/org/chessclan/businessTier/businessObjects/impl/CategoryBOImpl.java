/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.businessTier.businessObjects.impl;

import java.util.List;
import org.chessclan.businessTier.businessObjects.CategoryBO;
import org.chessclan.dataTier.models.Category;
import org.chessclan.dataTier.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Xcays
 */
@Service("CategoryBO")
public class CategoryBOImpl implements CategoryBO{    
    
    @Autowired
    private CategoryRepository categoryRepo;
    
    public Category saveCategory(Category c){
        return categoryRepo.save(c);
    }
    
    public Iterable<Category> saveCategories(Iterable<Category> c){
        return categoryRepo.save(c);
    }
    
    public Category findCategoryById(int id){
        return categoryRepo.findOne(id);
    }
    
    public Iterable<Category> findCategoriesById(Iterable<Integer> ids){
        return categoryRepo.findAll(ids);
    }
    
    public List<Category> findAll(){
        return categoryRepo.findAll();
    }
    
    public void deleteCategory(int id){
        categoryRepo.delete(id);
    }
    
    public void deleteCategory(Category c){
        categoryRepo.delete(c);
    }
    
    public void deleteCategories(Iterable<Category> cs){
        categoryRepo.delete(cs);
    }
}
