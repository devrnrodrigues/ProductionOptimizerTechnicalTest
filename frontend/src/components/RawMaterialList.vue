<template>
  <div>
    <h2>Raw Materials</h2>
    <RawMaterialForm ref="form" @refresh="fetchMaterials" />
    <table v-if="materials.length">
      <thead>
        <tr>
          <th>ID</th>
          <th>Name</th>
          <th>Stock</th>
          <th>Unit Cost</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="material in materials" :key="material.id">
          <td>{{ material.id }}</td>
          <td>{{ material.name }}</td>
          <td>{{ material.stockQuantity }}</td>
          <td>{{ material.unitCost }}</td>
          <td>
            <button @click="editMaterial(material)">Edit</button>
            <button @click="deleteMaterial(material.id)">Delete</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import RawMaterialForm from './RawMaterialForm.vue';
import rawMaterialService from '../services/rawMaterialService.js';

export default {
  components: { RawMaterialForm },
  data() {
    return { materials: [] };
  },
  methods: {
    fetchMaterials() {
      rawMaterialService
        .getAll()
        .then(res => (this.materials = res.data || []))
        .catch(() => (this.materials = []));
    },
    deleteMaterial(id) {
      if(confirm('Are you sure?')) {
        rawMaterialService.delete(id).then(this.fetchMaterials);
      }
    },
    editMaterial(material) {
      this.$refs.form.editMaterial(material);
    }
  },
  mounted() {
    this.fetchMaterials();
  }
};
</script>