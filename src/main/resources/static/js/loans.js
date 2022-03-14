const app = new Vue({
    el: "#app",
    data:{
        clientLoans:[],
        clients:[]
    },
    created(){
        this.loadData()
    },
    methods:{
        loadData(){
            axios.get('/api/clients/current')
            .then(res => {
                this.clients = res.data
                this.clientLoans = res.data.clientLoans
                console.log(this.clientLoans)
            })
            .catch(e => console.log(e))
        },
        loanApply(){
            return window.location.href = "/web/loan-application.html"
        },
        logout(){
            axios.post('/api/logout')
            .then(response => {
                console.log('signed out!!!')
                return window.location.href = "/web/index.html"
            })
            .catch(e => console.log(e))
        }
    }
})