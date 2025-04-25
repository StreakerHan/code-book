part of dashboard;

class _MainMenu extends StatelessWidget {
  const _MainMenu({
    required this.onSelected,
    Key? key,
  }) : super(key: key);

  final Function(int index, SelectionButtonData value) onSelected;

  @override
  Widget build(BuildContext context) {
    return SelectionButton(
      data: [
        SelectionButtonData(
          activeIcon: EvaIcons.person,
          icon: EvaIcons.personOutline,
          label: "个人资料",
          // totalNotif: 20,
        ),
        SelectionButtonData(
          activeIcon: EvaIcons.codeDownloadOutline,
          icon: EvaIcons.code,
          label: "签到",
        ),
        SelectionButtonData(
          activeIcon: EvaIcons.list,
          icon: EvaIcons.listOutline,
          label: "阅读历史",
          // totalNotif: 100,
        ),
        SelectionButtonData(
          activeIcon: EvaIcons.book,
          icon: EvaIcons.bookmarkOutline,
          label: "关于猿书网",
        ),
      ],
      onSelected: onSelected,
    );
  }
}
