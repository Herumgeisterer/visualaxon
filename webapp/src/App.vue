<template>
  <div :class="['app', 'app--' + $route.name]">
    <nav class="blue-grey lighten-1" role="navigation">
      <ul>
        <li><a v-link="{ name: 'home', exact: true }">Home</a></li>
        <li><a v-link="{ name: 'example' }">Example</a></li>
      </ul>
    </nav>

    <octocat></octocat>

    <router-view transition="fade" transition-mode="out-in"></router-view>
  </div>
</template>

<script>
  import Octocat from './components/Octocat.vue';

  export default {
    components: {
      Octocat
    },
    replace: false
  }
</script>

<style>
  .v-link-active {
    font-weight: bold;
  }

  .fade-transition {
    opacity: 1;
    transition: opacity 0.25s;
  }

  .fade-enter, .fade-leave {
    opacity: 0;
  }
</style>
