part of dashboard;

class _WeeklyTask extends StatelessWidget {
  const _WeeklyTask({
    required this.data,
    Key? key,
  }) : super(key: key);

  final List<BookModel> data;

  @override
  Widget build(BuildContext context) {
    return Obx(()=>Column(
      children: data
          .asMap()
          .entries
          .map(
            (e) => ListTaskAssigned(
          data: e.value,
        ),
      )
          .toList(),
    ));
  }
}
