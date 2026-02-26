<template>
  <div style="margin-bottom: 1.5rem;">
    <input v-model="product.name" placeholder="Product Name" />
    <input type="number" v-model.number="product.salePrice" placeholder="Sale Price" />
    <button @click="saveProduct">Save</button>
  </div>
</template>

<script>
import productService from '../services/productService.js';

export default {
  name: 'ProductForm',
  data() {
    return {
      product: { name: '', salePrice: 0 },
    };
  },
  methods: {
    saveProduct() {
      if(this.product.id) {
        productService.update(this.product.id, this.product).then(() => this.resetForm());
      } else {
        productService.create(this.product).then(() => this.resetForm());
      }
      this.$emit('refresh');
    },
    editProduct(product) {
      this.product = { ...product };
    },
    resetForm() {
      this.product = { name: '', salePrice: 0 };
    }
  }
};
</script>