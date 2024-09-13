<template>
  <div class="app-container">
    <el-button :disabled="orderInfo.orderInfoProject.status === 0 || orderInfo.orderInfoProject.status === 2 || orderInfo.orderInfoProject.status === 3 || orderInfo.orderInfoProject.status === 4" @click="onclickAuditOrder" type="success" plain>审核通过</el-button>
    <el-button :disabled="orderInfo.orderInfoProject.status === 0 || orderInfo.orderInfoProject.status === 2 || orderInfo.orderInfoProject.status === 3 || orderInfo.orderInfoProject.status === 4" @click="dialogFormVisible = true" type="danger" plain>审核不通过</el-button>
    <el-dialog title="请输入备注" :visible.sync="dialogFormVisible">
      <el-form ref="form" :model="postData">
        <el-form-item label="备注" prop="addition" :label-width="'100px'" :rules="[{ required: true, message: '请输入备注' }]">
          <el-input v-model="postData.addition" type="textarea" :rows="2" placeholder="请输入内容" ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="onclickAuditOrderError">确 定</el-button>
      </div>
    </el-dialog>
    <el-form ref="form" :inline="true" :model="orderInfo" label-width="80px">
      <p>投标项目信息：</p>
      <el-form-item label="项目编号">
        <el-input :readonly="true" v-model="orderInfo.projectInfo.projectId"></el-input>
      </el-form-item>
      <el-form-item label="项目名称">
        <el-input :readonly="true" v-model="orderInfo.projectInfo.projectName"></el-input>
      </el-form-item>
    </el-form>
    <el-form ref="form" :inline="true" :model="orderInfo" label-width="100px">
      <p>订单信息：</p>
      <el-form-item label="订单状态">
        <el-tag type="warning" v-if="orderInfo.orderInfoProject.status === 0">待付款</el-tag>
        <el-tag type="primary" v-if="orderInfo.orderInfoProject.status === 1">已付款</el-tag>
        <el-tag type="success" v-if="orderInfo.orderInfoProject.status === 2">已完成</el-tag>
        <el-tag type="info" v-if="orderInfo.orderInfoProject.status === 3">已失效</el-tag>
        <el-tag type="danger" v-if="orderInfo.orderInfoProject.status ===4">未通过</el-tag>
      </el-form-item>
      <el-form-item label="开票状态">
        <el-tag type="info" v-if="orderInfo.orderInfoProject.isToken === 0">不开票</el-tag>
        <el-tag type="danger" v-if="orderInfo.orderInfoProject.isToken === 1">待开票</el-tag>
        <el-tag type="warning" v-if="orderInfo.orderInfoProject.isToken === 2">已付款</el-tag>
        <el-tag v-if="orderInfo.orderInfoProject.isToken === 3">已开票</el-tag>
        <el-tag type="success" v-if="orderInfo.orderInfoProject.isToken === 4">已邮寄</el-tag>
      </el-form-item>
      <el-form-item label="订单总金额">
        <el-input :readonly="true" v-model="orderInfo.orderInfoProject.total">
          <template slot="append">元</template>
        </el-input>
      </el-form-item>
    </el-form>
    <el-form ref="form" :inline="true" :model="orderInfo" label-width="100px">
      <p>投标人信息：</p>
      <el-form-item label="联系人姓名">
        <el-input :readonly="true" v-model="orderInfo.users.name"></el-input>
      </el-form-item>
      <el-form-item label="手机">
        <el-input :readonly="true" v-model="orderInfo.users.phoneNum"></el-input>
      </el-form-item>
      <el-form-item label="固定电话">
        <el-input :readonly="true" v-model="orderInfo.users.telephone"></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input :readonly="true" v-model="orderInfo.users.email"></el-input>
      </el-form-item>
      <el-form-item label="企业名称">
        <el-input :readonly="true" v-model="orderInfo.users.unitname"></el-input>
      </el-form-item>
      <el-form-item label="企业性质">
        <el-input :readonly="true" v-model="orderInfo.users.companyStatus"></el-input>
      </el-form-item>
      <el-form-item label="企业地址">
        <el-input :readonly="true" v-model="orderInfo.users.companyAddress"></el-input>
      </el-form-item>
      <el-form-item label="开户行">
        <el-input :readonly="true" v-model="orderInfo.users.bank"></el-input>
      </el-form-item>
      <el-form-item label="银行卡号">
        <el-input :readonly="true" v-model="orderInfo.users.bankNumber"></el-input>
      </el-form-item>
      <el-form-item label="开户手机号">
        <el-input :readonly="true" v-model="orderInfo.users.bankPhone"></el-input>
      </el-form-item>
    </el-form>
    <el-form ref="form" :label-position="'top'" :model="orderInfo" label-width="80px">
      <el-form-item label="法定代表人授权书（加盖公章）">
        <el-image
          style="width: 100px; height: 100px"
          :src="orderInfo.fileAdditionMap.Auth.filePath"
          :preview-src-list="[orderInfo.fileAdditionMap.Auth.filePath]">
        </el-image>
      </el-form-item>
      <el-form-item label="身份证（正反两面）">
        <el-image
          style="width: 100px; height: 100px"
          :src="orderInfo.fileAdditionMap.ID0.filePath"
          :preview-src-list="[orderInfo.fileAdditionMap.ID0.filePath]">
        </el-image>
        <el-image
          style="width: 100px; height: 100px"
          :src="orderInfo.fileAdditionMap.ID1.filePath"
          :preview-src-list="[orderInfo.fileAdditionMap.ID0.filePath]">
        </el-image>
      </el-form-item>
      <el-form-item label="营业执照">
        <el-image
          style="width: 100px; height: 100px"
          :src="orderInfo.fileAdditionMap.Licen.filePath"
          :preview-src-list="[orderInfo.fileAdditionMap.Licen.filePath]">
        </el-image>
      </el-form-item>
      <el-form-item label="付款凭证">
        <el-image
          style="width: 100px; height: 100px"
          :src="orderInfo.orderInfoProject.orderPicture"
          :preview-src-list="[orderInfo.orderInfoProject.orderPicture]">
        </el-image>
      </el-form-item>
      <el-table
        :data="orderInfo.orderPackageList"
        border
        style="width: 100%">
        <el-table-column
          prop="index"
          label="序号"
          width="180">
          <template slot-scope="scope">
            {{ scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column
          prop="packageName"
          label="包件名称"
          width="180">
          <template slot-scope="scope">
            {{ scope.row.packageName }}
          </template>
        </el-table-column>
        <el-table-column
          prop="price"
          label="招标文件费（元）">
          <template slot-scope="scope">
            {{ scope.row.price }}
          </template>
        </el-table-column>
        <el-table-column
          prop="fileName"
          label="招标文件">
          <template slot-scope="scope">
            {{ scope.row.fileName }}
          </template>
        </el-table-column>
      </el-table>
    </el-form>
  </div>
</template>

<script>
import { getOrderInfo, updateOrderStatus, updateOrderStatusError } from '@/api/order_details'
import store from '@/store'

export default {
  data() {
    return {
      id: 0,
      orderInfo: null,
      orderPackageList: [],
      dialogFormVisible: false,
      postData: {
        id: 0,
        addition: null
      }
    }
  },
  created() {
    this.id = this.$route.params.id
    this.getOrderDetails()
  },
  methods: {
    getOrderDetails() {
      getOrderInfo(this.id).then(res => {
        this.orderInfo = res.data
        this.orderPackageList = res.data.orderPackageList
      })
    },
    onclickAuditOrder() {
      updateOrderStatus(this.id).then(res => {
        console.log(res)
        store.dispatch('user/getInfo')
        this.$router.go(0)
      })
    },
    onclickAuditOrderError() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          console.log('valid:', valid)
          console.log(valid)
          this.postData.id = this.id
          this.dialogFormVisible = false
          updateOrderStatusError(this.postData).then(res => {
            store.dispatch('user/getInfo')
            this.$router.go(0)
          })
        }
      })
    }
  }
}

</script>

<style scoped>

</style>
