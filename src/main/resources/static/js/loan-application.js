const app = new Vue({
    el: '#app',
    data:{
        clients: [],
        selected: '',
        loans: {},
        fee:[],
        loansMortage:{},
        loansPersonal:{},
        loansAutomotive:{},
        amount:0,
        destinationAccount:''

    },
    created(){
        this.loadData()
        this.loansData()
    },
    methods:{
        loadData(){
            axios.get('/api/clients/current')
            .then(res =>{
                this.clients = res.data
                console.log(this.clients)
            })
            .catch(e =>{
                console.log(e)
            })
        },
        loansData(){
            axios.get('/api/loans')
            .then(res =>{
                this.loansMortage = res.data[0]
                this.loansPersonal = res.data[1]
                this.loansAutomotive = res.data[2]
                console.log(this.loansMortage)
                console.table(this.loansMortage)
            })
            .catch(e => {
                console.log(e)
            })
        },
        loanApply(){
            axios.post('/api/loans', {'name':this.selected,'amount':this.amount,'payments':this.fee,'number':this.destinationAccount},
            { headers: { "content-type": "application/json" } })
            .then(res => {
                return window.location.href = '/web/loans.html'
            })
            .catch(e => {
                console.log(e)
            })
        }
    
    }
    
})