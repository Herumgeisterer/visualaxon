<template>
  <div class="view">
    <diagram></diagram>
  </div>
</template>

<script>
  import Diagram from './Diagram.vue';
  import store from '../store.js';

  export default {
    components: {
      Diagram
    },
    ready: function () {
      if (store.state.nodes) {
        var json = store.state.nodes;
        this.$broadcast('nodesRead', json);
      }
    }
  }
</script>
