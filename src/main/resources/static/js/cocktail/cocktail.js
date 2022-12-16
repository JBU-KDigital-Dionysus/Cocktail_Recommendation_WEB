async function ctList(page =1) {
    let url = "/cocktail/list.do?page=" + page;
    let resp = await fetch(url);
    if (resp.status == 200) {
        let textHtml = await resp.text();
        cocktailList.innerHTML = textHtml;
    } else {
        alert("칵테일 리스트를 불러올 수 없습니다.")
    }
}