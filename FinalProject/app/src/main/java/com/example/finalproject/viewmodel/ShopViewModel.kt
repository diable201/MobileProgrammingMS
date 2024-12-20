package com.example.finalproject.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.Category
import com.example.finalproject.model.Product
import com.example.finalproject.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ShopViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _selectedCategoryId = MutableStateFlow<Int?>(null)
    val selectedCategoryId: StateFlow<Int?> = _selectedCategoryId.asStateFlow()

    private val _filteredProducts = MutableStateFlow<List<Product>>(emptyList())
    val filteredProducts: StateFlow<List<Product>> = _filteredProducts.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        fetchCategoriesAndProducts()
        observeSelectedCategory()
    }

    private fun fetchCategoriesAndProducts() {
        viewModelScope.launch {
            try {
                val fetchedCategories = repository.getCategories()
                _categories.value = fetchedCategories
                Log.d("ShopViewModel", "Categories fetched: $fetchedCategories")

                if (_selectedCategoryId.value == null && fetchedCategories.isNotEmpty()) {
                    _selectedCategoryId.value = fetchedCategories[0].categoryId
                }

                val fetchedProducts = repository.getProducts()
                _products.value = fetchedProducts
                Log.d("ShopViewModel", "Products fetched: $fetchedProducts")
            } catch (e: Exception) {
                Log.e("ShopViewModel", "Error fetching data: ${e.message}")
                _error.value = "Failed to load data."
            }
        }
    }

    private fun observeSelectedCategory() {
        viewModelScope.launch {
            combine(
                _categories, _products, _selectedCategoryId
            ) { categories, products, selectedCategoryId ->
                val category = categories.find { it.categoryId == selectedCategoryId }
                if (category != null) {
                    products.filter {
                        it.category.equals(category.categoryName, ignoreCase = true)
                    }
                } else {
                    emptyList()
                }
            }.collect { filtered ->
                _filteredProducts.value = filtered
                Log.d("ShopViewModel", "Filtered Products: $filtered")
            }
        }
    }

    fun selectCategory(categoryId: Int) {
        _selectedCategoryId.value = categoryId
        Log.d("ShopViewModel", "Selected categoryId: $categoryId")
    }

    fun refreshData() {
        fetchCategoriesAndProducts()
    }
}