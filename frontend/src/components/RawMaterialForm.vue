<template>
  <div style="margin-bottom: 1.5rem;">
    <input v-model="material.name" placeholder="Name" />
    <input type="number" v-model.number="material.stockQuantity" placeholder="Stock Quantity" />
    <input type="number" v-model.number="material.unitCost" placeholder="Unit Cost" />
    <button @click="saveMaterial">Save</button>
  </div>
</template>

<script>
import rawMaterialService from '../services/rawMaterialService';

export default {
  name: 'RawMaterialForm',
  data() {
    return {
      material: { name: '', stockQuantity: 0, unitCost: 0 }
    };
  },
  methods: {
    saveMaterial() {
      if(this.material.id) {
        rawMaterialService.update(this.material.id, this.material).then(() => this.resetForm());
      } else {
        rawMaterialService.create(this.material).then(() => this.resetForm());
      }
      this.$emit('refresh');
    },
    editMaterial(material) {
      this.material = { ...material };
    },
    resetForm() {
      this.material = { name: '', stockQuantity: 0, unitCost: 0 };
    }
  }
};
</script>