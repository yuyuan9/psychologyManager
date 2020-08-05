

Vue.component('star-rating', {
    template: '#star-rating',
    props: ['max', 'current'],
    computed: {
        getRating: function() {
        return (this.current / this.max) * 100
        }
    }
})