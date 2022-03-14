const app = new Vue({
    el:"#app",
    data:{
        loanName:"",
        maxAmount: 0,
        payments: [],
        percentage:0 
    },
    methods:{
        createNewLoan(){
            axios.post("/api/newLoan", {'name': this.loanName, 'maxAmount': this.maxAmount,'payments': this.payments,'percentage': this.percentage},
            {headers: {"content-type":"application/json"}} )
            .then(res => {
                return window.location.href= "/web/manager.html"
                console.log(res)
            })
            .catch(e => {
                console.log(e)
            })
        }
    }
})