#作業要求
使用ＭVVM架構
  Model: PageagmModel
  ViewModel: PageViewModel
  View: ProductListFragment
  
模擬打api取得一包假資料呈現畫面中的列表
  實作MockInterceptor讀取 res/raw/mock_data.json

edge to edge
  在MainActivity實作

第一次畫面出現 logcat印出畫面所有viewHolder可視率
   於GridLayoutManager的onLayoutCompleted callback時實作

每當滑動停止 logcat印出畫面所有viewHolder可視率
   於RecyclerView.OnScrollListener onScrollStateChanged時實作

filter bar(灰色區塊)具有sticky header(吸頂)效果
   透過CoordinatorLayout widget實作
