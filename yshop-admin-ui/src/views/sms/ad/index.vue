<template>
  <div class="app-container">
    <!-- 搜索表单::begin -->
    <el-form :inline="true" ref="queryForm">
      <el-form-item>
        <el-button type="primary" @click="handleAdd">新增</el-button>
        <el-button type="success" @click="handleEdit" :disabled="single">修改</el-button>
        <el-button type="danger" @click="handleDelete" :disabled="multiple">删除</el-button>
      </el-form-item>
      <el-form-item label="广告名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="广告名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">查询</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 搜索表单::end -->

    <!-- 数据表格::start -->
    <el-table
      id="dataTable"
      ref="multipleTable"
      :data="tableData"
      @selection-change="handleSelectionChange"
      @row-click="handleRowClick"
      border>
      <el-table-column
        type="selection"
        min-width="5%">
      </el-table-column>
      <el-table-column
        prop="id"
        label="编号"
        min-width="10">
      </el-table-column>
      <el-table-column
        prop="name"
        label="广告名称"
        min-width="10">
      </el-table-column>
      <el-table-column
        label="广告图片"
        min-width="10">
        <template slot-scope="scope">
          <el-popover
            placement="right"
            title=""
            trigger="hover">
            <img :src="scope.row.pic_url"/>
            <img slot="reference" :src="scope.row.pic_url" :alt="scope.row.pic_url"
                 style="max-height: 60px;max-width: 60px">
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
        prop="start_time"
        label="开始时间"
        min-width="10">
      </el-table-column>
      <el-table-column
        prop="end_time"
        label="到期时间"
        min-width="10">
      </el-table-column>
      <el-table-column
        prop="status"
        label="上线/下线"
        min-width="10">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            :active-value="1"
            :inactive-value="0"
            @change="handleStatusChange(scope.row)">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column
        prop="sort"
        label="排序"
        min-width="10">
      </el-table-column>
      <el-table-column label="操作" min-width="15%">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.row)">编辑
          </el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 数据表格::end-->

    <!-- 分页工具条::start -->
    <pagination
      v-show="pagination.total>0"
      :total="pagination.total"
      :page.sync="pagination.page"
      :limit.sync="pagination.limit"
      @pagination="handleQuery"/>
    <!-- 分页工具条::end -->

    <!-- 表单弹窗::start -->
    <el-dialog
      :title="dialog.title"
      :visible.sync="dialog.visible"
      @close="cancel"
      :append-to-body="true"
      top="5vh"
      width="40%">
      <el-form id="dataForm" label-width="140px" :model="form" :rules="rules" ref="form">
        <el-form-item label="广告名称" required prop="name">
          <el-input v-model="form.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="开始时间" required prop="start_time">
          <el-date-picker
            v-model="form.start_time"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="到期时间" required prop="end_time">
          <el-date-picker
            v-model="form.end_time"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="广告图片" prop="pic_url">
          <single-upload v-model="form.pic_url"></single-upload>
        </el-form-item>
        <el-form-item label="上线/下线" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">上线</el-radio>
            <el-radio :label="0">下线</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input v-model="form.sort" auto-complete="off" style="width: 200px"></el-input>
        </el-form-item>
        <el-form-item label="广告链接" required prop="link_url">
          <el-input v-model="form.link_url" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" v-model="form.remark" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="handleSubmit">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {adPageList, adAdd, adDetail, adUpdate, adDelete,adStatusUpdate} from '@/api/sms/ad'
  import SingleUpload from '@/components/Upload/singleUpload'

  export default {
    components: {SingleUpload},
    data() {
      return {
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        pagination: {
          page: 1,
          limit: 10,
          total: 0
        },
        queryParams: {
          name: undefined
        },
        tableData: [],
        dialog: {
          title: '',
          visible: false,
        },
        form: {
          id: undefined,
          name: undefined,
          pic_url: undefined,
          start_time: undefined,
          end_time: undefined,
          sort: undefined,
          status: 1,
          link_url:undefined,
          remark:undefined
        },
        rules: {
          name: [{
            required: true, message: '请填写广告名称', trigger: 'blur'
          }],
          start_time: [{
            required: true, message: '请设置开始时间', trigger: 'blur'
          }],
          end_time: [{
            required: true, message: '请设置到期时间', trigger: 'blur'
          }],
          link_url: [{
            required: true, message: '请输入广告链接', trigger: 'blur'
          }],
        }
      }
    },
    created() {
      this.handleQuery()
    },
    methods: {
      handleQuery() {
        adPageList(this.pagination.page, this.pagination.limit, this.queryParams).then(response => {
          this.tableData = response.data.records
          this.pagination.total = response.data.total
        })
      },
      resetQuery() {
        this.pagination = {
          page: 1,
          limit: 10,
          total: 0
        }
        this.queryParams = {
          name: undefined
        }
        this.resetForm("queryForm")
        this.handleQuery()
      },
      handleAdd() {
        this.handleResetForm()
        this.dialog = {
          title: "新增广告",
          visible: true
        }
      },
      handleEdit(row) {
        this.handleResetForm();
        this.dialog = {
          title: "修改广告",
          visible: true
        }
        const id = row.id || this.ids
        adDetail(id).then(response => {
          if (response.data) {
            this.form = response.data
            if (response.data.start_time && response.data.end_time) {
              this.form.time = [new Date(response.data.start_time), new Date(response.data.end_time)]
            }
          }
        })
      },
      handleSubmit() {
        this.$refs["form"].validate((valid) => {
          if (valid) {
            const id = this.form.id
            if (id != undefined) {
              adUpdate(id, this.form).then(response => {
                this.$message.success(response.msg)
                this.dialog.visible = false
                this.handleQuery()
              })
            } else {
              adAdd(this.form).then(response => {
                this.$message.success(response.msg)
                this.dialog.visible = false
                this.handleQuery()
              })
            }
          }
        })
      },
      handleDelete(row) {
        const ids = row.id || this.ids
        this.$confirm('是否确认删除选中的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          adDelete(ids).then(() => {
            this.$message.success("删除成功")
            this.handleQuery()
          })
        }).catch(() =>
          this.$message.info("已取消删除")
        )
      },
      // 显示隐藏
      handleStatusChange(row) {
        let text = row.status === 0 ? '下线' : '上线'
        let that = this
        this.$confirm('确认要' + text + row.name + '广告?', "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          adStatusUpdate(row.id, row.status).then(response => {
            that.$message.success(response.msg)
          })
        }).catch(function () {
          row.status = row.status === 0 ? 1 : 0;
        })
      },
      handleRowClick(row) {
        this.$refs.multipleTable.toggleRowSelection(row);
      },
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        this.single = selection.length != 1
        this.multiple = !selection.length
      },
      cancel() {
        this.dialog.visible = false;
        this.handleResetForm()
      },
      handleResetForm() {
        this.resetForm("form")
        this.form = {
          id: undefined,
          name: undefined,
          pic_url: undefined,
          start_time: undefined,
          end_time: undefined,
          sort: undefined,
          status:1,
          link_url:undefined,
          remark:undefined
        }
      },
      initPate() {
        this.handleQuery()
      }
    }
  }
</script>

<style scoped>

</style>
