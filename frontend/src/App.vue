<script setup lang="ts">
import { ref, computed } from 'vue'

// Dummy Data
const products = ref([
  { id: 1, name: 'Premium Headphones', category: 'Electronics', price: 299, rating: 4.8, image: 'https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=500&q=80' },
  { id: 2, name: 'Minimalist Watch', category: 'Accessories', price: 150, rating: 4.5, image: 'https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=500&q=80' },
  { id: 3, name: 'Eco-Friendly Bottle', category: 'Home', price: 25, rating: 4.2, image: 'https://images.unsplash.com/photo-1602143307185-8c1f543955c1?w=500&q=80' },
  { id: 4, name: 'Leather Backpack', category: 'Accessories', price: 120, rating: 4.7, image: 'https://images.unsplash.com/photo-1548036230-da63990664d5?w=500&q=80' },
  { id: 5, name: 'Smart Speaker', category: 'Electronics', price: 80, rating: 4.3, image: 'https://images.unsplash.com/photo-1589492477829-5e65395b66cc?w=500&q=80' },
  { id: 6, name: 'Mechanical Keyboard', category: 'Electronics', price: 180, rating: 4.9, image: 'https://images.unsplash.com/photo-1511467687858-23d96c32e4ae?w=500&q=80' },
])

const searchQuery = ref('')
const selectedCategory = ref('All')
const categories = ['All', 'Electronics', 'Accessories', 'Home']

const filteredProducts = computed(() => {
  return products.value.filter(p => {
    const matchesSearch = p.name.toLowerCase().includes(searchQuery.value.toLowerCase())
    const matchesCategory = selectedCategory.value === 'All' || p.category === selectedCategory.value
    return matchesSearch && matchesCategory
  })
})

const cartCount = ref(0)
const addToCart = () => {
  cartCount.value++
}
</script>

<template>
  <div class="min-h-screen bg-slate-50 font-sans text-slate-900">
    <!-- Navbar -->
    <header class="sticky top-0 z-50 bg-white/80 backdrop-blur-md border-b border-slate-200">
      <div class="max-w-7xl mx-auto px-4 h-16 flex items-center justify-between gap-4">
        <!-- Logo -->
        <div class="flex items-center gap-2 font-bold text-xl tracking-tight text-indigo-600">
          <svg xmlns="http://www.w3.org/2000/svg" class="w-8 h-8" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 11V7a4 4 0 00-8 0v4M5 9h14l1 12H4L5 9z" />
          </svg>
          <span class="hidden sm:block">ShopFlow</span>
        </div>

        <!-- Search Bar -->
        <div class="flex-1 max-w-2xl relative">
          <span class="absolute inset-y-0 left-0 pl-3 flex items-center text-slate-400">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
            </svg>
          </span>
          <input 
            v-model="searchQuery"
            type="text" 
            placeholder="Search products..." 
            class="w-full pl-10 pr-4 py-2 bg-slate-100 border-none rounded-2xl focus:ring-2 focus:ring-indigo-500 transition-all outline-none"
          />
        </div>

        <!-- Cart -->
        <button class="relative p-2 text-slate-600 hover:text-indigo-600 transition-colors">
          <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z" />
          </svg>
          <span v-if="cartCount > 0" class="absolute -top-1 -right-1 bg-indigo-600 text-white text-[10px] font-bold px-1.5 py-0.5 rounded-full ring-2 ring-white">
            {{ cartCount }}
          </span>
        </button>
      </div>
    </header>

    <div class="max-w-7xl mx-auto px-4 py-8 grid grid-cols-1 lg:grid-cols-4 gap-8">
      <!-- Sidebar Filters -->
      <aside class="lg:col-span-1 space-y-8">
        <div>
          <h3 class="text-lg font-semibold mb-4">Categories</h3>
          <div class="flex flex-col gap-2">
            <button 
              v-for="cat in categories" 
              :key="cat"
              @click="selectedCategory = cat"
              :class="[
                'px-4 py-2 rounded-xl text-left transition-all',
                selectedCategory === cat ? 'bg-indigo-600 text-white shadow-lg shadow-indigo-200' : 'bg-white hover:bg-slate-100'
              ]"
            >
              {{ cat }}
            </button>
          </div>
        </div>

        <div>
          <h3 class="text-lg font-semibold mb-4">Price Range</h3>
          <input type="range" min="0" max="500" class="w-full h-2 bg-slate-200 rounded-lg appearance-none cursor-pointer accent-indigo-600" />
          <div class="flex justify-between text-sm text-slate-500 mt-2">
            <span>$0</span>
            <span>$500+</span>
          </div>
        </div>

        <div>
          <h3 class="text-lg font-semibold mb-4">Rating</h3>
          <div class="space-y-2">
            <label class="flex items-center gap-2 cursor-pointer group">
              <input type="checkbox" class="w-5 h-5 rounded border-slate-300 text-indigo-600 focus:ring-indigo-500" />
              <span class="text-slate-600 group-hover:text-slate-900 transition-colors">4 Stars & Above</span>
            </label>
            <label class="flex items-center gap-2 cursor-pointer group">
              <input type="checkbox" class="w-5 h-5 rounded border-slate-300 text-indigo-600 focus:ring-indigo-500" />
              <span class="text-slate-600 group-hover:text-slate-900 transition-colors">3 Stars & Above</span>
            </label>
          </div>
        </div>
      </aside>

      <!-- Results Area -->
      <main class="lg:col-span-3">
        <!-- Grid -->
        <div v-if="filteredProducts.length > 0" class="grid grid-cols-1 sm:grid-cols-2 xl:grid-cols-3 gap-6">
          <div 
            v-for="product in filteredProducts" 
            :key="product.id"
            class="group bg-white rounded-3xl border border-slate-100 overflow-hidden hover:shadow-2xl hover:shadow-slate-200 transition-all duration-500"
          >
            <!-- Image Container -->
            <div class="relative aspect-square overflow-hidden bg-slate-100">
              <img 
                :src="product.image" 
                :alt="product.name"
                class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-700"
              />
              <div class="absolute top-4 right-4 bg-white/90 backdrop-blur px-2 py-1 rounded-lg text-xs font-bold text-slate-900 shadow-sm">
                ★ {{ product.rating }}
              </div>
            </div>

            <!-- Content -->
            <div class="p-6">
              <div class="text-xs font-medium text-indigo-600 mb-1 tracking-wider uppercase">
                {{ product.category }}
              </div>
              <h4 class="text-lg font-bold text-slate-900 mb-2 group-hover:text-indigo-600 transition-colors">
                {{ product.name }}
              </h4>
              <div class="flex items-center justify-between mt-4">
                <span class="text-2xl font-black text-slate-900">
                  ${{ product.price }}
                </span>
                <button 
                  @click="addToCart"
                  class="bg-indigo-600 text-white p-3 rounded-2xl hover:bg-slate-900 hover:scale-105 active:scale-95 transition-all shadow-lg shadow-indigo-100"
                >
                  <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
                  </svg>
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- Empty State -->
        <div v-else class="flex flex-col items-center justify-center py-24 text-center">
          <div class="w-24 h-24 bg-indigo-50 text-indigo-600 rounded-full flex items-center justify-center mb-6">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-12 w-12" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.172 9.172a4 4 0 015.656 0M9 10h.01M15 10h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
            </svg>
          </div>
          <h3 class="text-2xl font-bold text-slate-900 mb-2">No products found</h3>
          <p class="text-slate-500 max-w-xs mx-auto">
            Try adjusting your search query or filters to find what you're looking for.
          </p>
          <button 
            @click="searchQuery = ''; selectedCategory = 'All'"
            class="mt-8 text-indigo-600 font-semibold hover:text-indigo-800 transition-colors underline underline-offset-4"
          >
            Clear all filters
          </button>
        </div>
      </main>
    </div>
  </div>
</template>

<style>
/* Custom transitions for a premium feel */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

::-webkit-scrollbar {
  width: 8px;
}

::-webkit-scrollbar-track {
  background: transparent;
}

::-webkit-scrollbar-thumb {
  background: #e2e8f0;
  border-radius: 10px;
}

::-webkit-scrollbar-thumb:hover {
  background: #cbd5e1;
}
</style>
