cascade = 영속성 전이
===
 - ALL
 - PERSIST
 - MERGE
 - REMOVE 
 - REFRESH
 - DETACH

---
 - orphan removal true : 특정 Entity를 삭제할 때, 해당 Entity와 연관관계를 가진 Entity를 삭제하는 경우
 - remove cascade : 특정 Entity를 삭제할 때, 해당 Entity와 연관관계를 가진 Entity를 삭제 하지 않는 경우