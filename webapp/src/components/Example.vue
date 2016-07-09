<template>
  <div class="example">
    <diagram></diagram>
  </div>
</template>

<script>
  import Diagram from './Diagram.vue';
  var json = require("../data/example.json");

  export default {
    components: {
      Diagram
    },
    ready: function () {
      this.$broadcast('nodesRead', json);
    }
  }
</script>
