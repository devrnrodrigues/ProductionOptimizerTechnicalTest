<template>
  <div>
    <h2>Products</h2>
    <ProductForm ref="form" @refresh="fetchProducts" />
    <table v-if="products.length">
      <thead>
        <tr>
          <th>ID</th>
          <th>Name</th>
          <th>Sale Price</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="product in products" :key="product.id">
          <td>{{ product.id }}</td>
          <td>{{ product.name }}</td>
          <td>{{ product.salePrice }}</td>
          <td>
            <button @click="editProduct(product)">Edit</button>
            <button @click="deleteProduct(product.id)">Delete</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import ProductForm from './ProductForm.vue';
import productService from '../services/productService.js';

export default {
  components: { ProductForm },
  data() {
    return {
      products: [],
      editItem: null,
    };
  },
  methods: {
    fetchProducts() {
      productService
        .getAll()
        .then(res => (this.products = res.data || []))
        .catch(() => (this.products = []));
    },
    deleteProduct(id) {
      if(confirm('Are you sure?')) {
        productService.delete(id).then(this.fetchProducts);
      }
    },
    editProduct(product) {
      this.editItem = product;
      this.$refs.form.editProduct(product);
    }
  },
  mounted() {
    this.fetchProducts();
  }
};
</script>